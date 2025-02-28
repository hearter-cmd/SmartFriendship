package com.yaonie.intelligent.assessment.server.chat_server.websocket.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yaonie.intelligent.assessment.server.chat_server.common.event.UserOnlineEvent;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.Message;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactStatusEnum;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.UserContact;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserContactService;
import com.yaonie.intelligent.assessment.server.chat_server.utils.IpUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.adepter.WebSocketAdepter;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.dto.WSChannelDTO;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums.WSRespTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSLoginUrl;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSSetSession;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.utils.NettyUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.utils.SessionUtil;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.area.GaoDeArea;
import com.yaonie.intelligent.assessment.system.mapper.UserMapper;
import io.micrometer.common.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant.USER_LOGIN_STATE;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-16 0:42
 * @Author 武春利
 * @CreateTime 2024-09-16
 * @ClassName WebSocketServiceImpl
 * @Project backend
 * @Description : WebSocket服务实现类
 */
@Service
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {
    @Resource
    @Lazy
    private WxMpService wxMpService;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 魔法值
     */
    public static final int MAXIMUM_SIZE = 1000;
    public static final Duration DURATION = Duration.ofHours(3);
    /**
     * 管理所有的在线链接
     * 登录态游客
     */
    public static final ConcurrentHashMap<Channel, WSChannelDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();
    /**
     * 临时保存登录code和channel映射
     */
    public static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(DURATION)
            .build();
    /**
     * 所有在线的用户和对应的socket
     * 格式： key: userId, value: channel
     */
    private static final ConcurrentHashMap<Long, CopyOnWriteArrayList<Channel>> ONLINE_UID_MAP = new ConcurrentHashMap<>();
    /**
     * 群聊对应用户
     */
    private static final ConcurrentHashMap<Long, CopyOnWriteArrayList<Long>> ONLINE_GROUP_MAP = new ConcurrentHashMap<>();
    @Autowired
    private UserContactService userContactService;

    /**
     * 添加Channel操作
     *
     * @param channel 通道
     */
    @Override
    public void addChannel(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WSChannelDTO());
    }

    /**
     * 微信用户扫码登录操作
     *
     * @param channel 通道
     */
    @Override
    public void handleWxUserLogin(Channel channel) throws WxErrorException {
//        if (WAIT_LOGIN_MAP.get())
        // 生成随机的唯一标识
        Integer code = generateLoginCode(channel);
        // 生成微信登录二维码URL, 第一个参数就是 callback 的时候携带的code
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) DURATION.toSeconds());
        // 发送URL给客户端
        WSLoginUrl wsLoginUrl = new WSLoginUrl(wxMpQrCodeTicket.getUrl());
        WebSocketAdepter.sendWSTextMsg(channel, WSRespTypeEnum.LOGIN_URL, wsLoginUrl);
    }

    /**
     * 离线之后的移除操作
     * @param channel websocket通道
     */
    @Override
    public void remove(Channel channel) {
        WSChannelDTO remove = ONLINE_WS_MAP.remove(channel);
        if (Objects.isNull(remove)) {
            return;
        }
        Long uid = remove.getUid();
        if (Objects.isNull(uid)) {
            return;
        }
        ONLINE_UID_MAP.remove(uid);
    }

    /**
     * 扫码登录成功进行的操作
     * @param code 事件码
     * @param uid 用户openid
     */
    @Override
    public void scanLoginSuccess(Integer code, Long uid) {
        // 确认机器链接还在, 并校验Code
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)) {
            return;
        }
        User userInfo = userMapper.selectById(uid);
        Session session = findByChannelSessionId(channel);
        if (session == null) {
            WebSocketAdepter.sendUnAuthorizeSuccessMsg(channel, "登录失败");
            return;
        }
        String ip = NettyUtil.getChannelAttr(channel, NettyUtil.TypeEnum.IP);
        GaoDeArea gaoDeArea = IpUtil.getIpAreaByGaoDe(ip);
        if (gaoDeArea != null) {
            userInfo.setAreaName(gaoDeArea.getProvince());
            userInfo.setAreaCode(gaoDeArea.getAdcode());
        }
        // 发送登录成功消息
        loginSuccess(channel, userInfo, session);
        // 登录成功, 移除Code
        WAIT_LOGIN_MAP.invalidate(code);
    }

    /**
     * 在 链接成功 之后, 进行验证
     *
     * @param channel 通道
     * sessionId存在, 并且存储了USER_LOGIN_STATE状态, 才能算是验证成功
     *                否则就是失败
     */
    @Override
    public void authorize(Channel channel) {
        // 在握手的时候就已经绑定过sessionId, 没有就说明是未登录用户
        Attribute<Object> sessionIdAttr = channel.attr(AttributeKey.valueOf("SESSION"));
        String sessionId = sessionIdAttr.get().toString();
        log.info("当前解析后的sessionId:{}", new String(Base64.getDecoder().decode(sessionId)));
        // SessionId本来就不存在, 就创建sessionId
        if (StringUtils.isBlank(sessionId)) {
            channel.attr(AttributeKey.valueOf("SESSION")).set(sessionId);
        } else {
            Session session = SessionUtil.findSessionBySessionId(sessionId);
            // Session存在就直接使用, 不存在就创建session并绑定给channel
            if (Objects.isNull(session)) {
                // 没有登录, 就创建一个新的session等待登录
                sessionId = SessionUtil.createSessionId();
                sessionIdAttr.set(sessionId);
            } else {
                // 检验是否登录
                Object attribute = session.getAttribute(USER_LOGIN_STATE);
                if (!Objects.isNull(attribute)) {
                    loginSuccess(channel, (User) attribute, session);
                    return;
                } else {
                    return;
                }
            }
        }
        WebSocketAdepter.sendWSTextMsg(channel, WSRespTypeEnum.INVALIDATE_TOKEN, new WSSetSession(sessionId));
    }

    /**
     * 处理消息
     * @param message  消息
     */
    @Override
    public void handleMsg(Message message) {
        // todo 处理消息
        // 获取要发送给谁
        Long contactId = message.getContactId();
        CopyOnWriteArrayList<Channel> channels = ONLINE_UID_MAP.get(contactId);
        if (channels != null) {
            channels.forEach(channel -> {
                WebSocketAdepter.sendWSTextMsg(channel, WSRespTypeEnum.MESSAGE, message);
            });
        } else if (UserContactTypeEnum.getEnumByLen(contactId) == UserContactTypeEnum.GROUP) {
            // 创建群聊
            ONLINE_GROUP_MAP.put(contactId, new CopyOnWriteArrayList<>(Collections.singleton(message.getUserId())));
        }
    }

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 登录成功
     * @param channel websocket通道
     * @param userInfo 用户信息
     * @param session session
     */
    @Override
    public void loginSuccess(Channel channel, User userInfo, Session session) {
        Long userId = userInfo.getId();
        WSChannelDTO wsChannelDTO = ONLINE_WS_MAP.get(channel);
        wsChannelDTO.setUid(userId);
        // todo 用户上线成功的事件
        // 更新用户实时信息, 并绑定channel与session的关系
        userInfo.refreshIp(NettyUtil.getChannelAttr(channel, NettyUtil.TypeEnum.IP));
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(userInfo.getUserAccount());
        session.setAttribute(USER_LOGIN_STATE, securityUser);
        SessionUtil.saveSession(session);

        // 创建认证令牌
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                securityUser, securityUser.getPassword(), securityUser.getAuthorities());

        // 创建安全上下文
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationToken);

        // 将安全上下文存储到指定的 Session 中
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

        applicationEventPublisher.publishEvent(new UserOnlineEvent(this, userInfo));
        // 将在线状态的用户channel存储到map
        CopyOnWriteArrayList<Channel> channels = ONLINE_UID_MAP.get(userId);
        if (Objects.isNull(channels)) {
            channels = new CopyOnWriteArrayList<>();
            ONLINE_UID_MAP.put(userId, channels);
            channels.add(channel);
        }  else {
            channels.add(channel);
        }
        List<UserContact> list = userContactService.lambdaQuery()
                .eq(UserContact::getUserId, userId)
                .eq(UserContact::getContactType, UserContactTypeEnum.GROUP.getType())
                .eq(UserContact::getStatus, UserContactStatusEnum.FRIEND)
                .list();
        list.forEach(userContact -> {
            CopyOnWriteArrayList<Long> longs = ONLINE_GROUP_MAP.get(userContact.getContactId());
            if (longs == null) {
                longs = new CopyOnWriteArrayList<>();
                longs.add(userId);
                ONLINE_GROUP_MAP.put(userContact.getContactId(), longs);
            } else {
                longs.add(userId);
            }
        });
        // 发送登录成功消息
        WebSocketAdepter.sendWSLoginSuccessMsg(channel, userInfo);
    }

    private Integer generateLoginCode(Channel channel) {
        Integer code;
        // 生成随机标识, 并必然不重复
        do {
            code = RandomUtil.randomInt(Integer.MAX_VALUE);
        } while (!Objects.isNull(WAIT_LOGIN_MAP.asMap().putIfAbsent(code, channel)));
        return code;
    }

    private Session findByChannelSessionId(Channel channel) {
        String sessionId = channel.attr(AttributeKey.valueOf("SESSION")).get().toString();
        Session session = SessionUtil.findSessionBySessionId(sessionId);
        return session;
    }

}
