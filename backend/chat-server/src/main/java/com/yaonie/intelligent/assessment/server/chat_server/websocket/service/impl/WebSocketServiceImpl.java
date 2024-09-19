package com.yaonie.intelligent.assessment.server.chat_server.websocket.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yaonie.intelligent.assessment.server.chat_server.common.event.UserOnlineEvent;
import com.yaonie.intelligent.assessment.server.chat_server.user.mappers.UserMapper;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.LoginService;
import com.yaonie.intelligent.assessment.server.chat_server.utils.IpUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.adepter.WebSocketAdepter;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.dto.WSChannelDTO;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums.WSRespTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSLoginUrl;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.utils.NettyUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.utils.SessionUtil;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.area.GaoDeArea;
import io.micrometer.common.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import jakarta.annotation.Resource;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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
public class WebSocketServiceImpl implements WebSocketService {
    /**
     * 管理所有的在线链接
     * 登录态游客
     */
    public static final ConcurrentHashMap<Channel, WSChannelDTO> ONLINE_WS_MAP = new ConcurrentHashMap<>();
    /**
     * 临时保存登录code和channel映射
     */
    public static final int MAXIMUM_SIZE = 1000;
    public static final Duration DURATION = Duration.ofHours(3);
    public static final Cache<Integer, Channel> WAIT_LOGIN_MAP = Caffeine.newBuilder()
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(DURATION)
            .build();
    @Resource
    @Lazy
    private WxMpService wxMpService;
    @Resource
    private LoginService loginService;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

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

    @Override
    public void remove(Channel channel) {
        ONLINE_WS_MAP.remove(channel);
    }

    @Override
    public void scanLoginSuccess(Integer code, Long uid) {
        // 确认机器链接还在, 并校验Code
        Channel channel = WAIT_LOGIN_MAP.getIfPresent(code);
        if (Objects.isNull(channel)) {
            return;
        }
        User userInfo = userMapper.selectById(uid);
        // 调用登录模块获取token
        Session session = findByChannelSessionId(channel);
        if (session == null) {
            WebSocketAdepter.sendUnAuthorizeSuccessMsg(channel, "登录失败");
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
     * @param channel
     */
    @Override
    public void authorize(Channel channel) {
        // 在握手的时候就已经绑定过sessionId, 没有就说明是未登录用户
        Attribute<Object> sessionIdAttr = channel.attr(AttributeKey.valueOf("SESSION"));
        String sessionId = sessionIdAttr.get().toString();
        // SessionId本来就不存在, 就创建sessionId
        if (StringUtils.isBlank(sessionId)) {
            String newSessionId = SessionUtil.createSessionId();
            channel.attr(AttributeKey.valueOf("SESSION")).set(newSessionId);
            WebSocketAdepter.sendWSTextMsg(channel, WSRespTypeEnum.INVALIDATE_TOKEN, null);
        }
        Session session = SessionUtil.findSessionBySessionId(sessionIdAttr.get().toString());
        // Session存在就直接使用, 不存在就创建session并绑定给channel
        if (!Objects.isNull(session)) {
            Object attribute = session.getAttribute(USER_LOGIN_STATE);
            if (!Objects.isNull(attribute)) {
                loginSuccess(channel, (User) attribute, session);
                return;
            }
        }
        WebSocketAdepter.sendWSTextMsg(channel, WSRespTypeEnum.INVALIDATE_TOKEN, null);
    }

    public void loginSuccess(Channel channel, User userInfo, Session session) {
        WSChannelDTO wsChannelDTO = ONLINE_WS_MAP.get(channel);
        wsChannelDTO.setUid(userInfo.getId());
        // todo 用户上线成功的事件
        userInfo.refreshIp(NettyUtil.getChannelAttr(channel, NettyUtil.TypeEnum.IP));
        session.setAttribute(USER_LOGIN_STATE, userInfo);
        SessionUtil.saveSession(session);
        applicationEventPublisher.publishEvent(new UserOnlineEvent(this, userInfo));
        WebSocketAdepter.sendWSLoginSuccessMsg(channel, userInfo);
    }

    private Integer generateLoginCode(Channel channel) {
        Integer code = null;
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
