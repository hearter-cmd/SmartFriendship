package com.yaonie.intelligent.assessment.server.chat_server.websocket.service.impl;


import cn.hutool.core.util.RandomUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.mappers.UserMapper;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.LoginService;
import com.yaonie.intelligent.assessment.server.chat_server.utils.SessionUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.adepter.WebSocketAdepter;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.dto.WSChannelDTO;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums.WSRespTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSLoginUrl;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.util.RedisUtils;
import io.netty.channel.Channel;
import jakarta.annotation.Resource;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.context.annotation.Lazy;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisSessionRepository;
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
    @Resource
    @Lazy
    private WxMpService wxMpService;
    @Resource
    private LoginService loginService;
    @Resource
    private UserMapper userMapper;

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

    /**
     * 添加Channel操作
     * @param channel 通道
     */
    @Override
    public void addChannel(Channel channel) {
        ONLINE_WS_MAP.put(channel, new WSChannelDTO());
    }

    /**
     * 微信用户扫码登录操作
     * @param channel 通道
     */
    @Override
    public void handleWxUserLogin(Channel channel) throws WxErrorException {
//        if (WAIT_LOGIN_MAP.get())
        // 生成随机的唯一标识
        Integer code = generateLoginCode(channel);
        // 生成微信登录二维码URL, 第一个参数就是 callback 的时候携带的code
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(code, (int) DURATION.toSeconds() );
        // 发送URL给客户端
        WSLoginUrl wsLoginUrl = new WSLoginUrl(wxMpQrCodeTicket.getUrl());
        WebSocketAdepter.sendWSTextMsg(channel, WSRespTypeEnum.LOGIN_SUCCESS, wsLoginUrl);
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
        String token = loginService.login(uid);
        // 发送登录成功消息
        WebSocketAdepter.sendWSLoginSuccessMsg(channel, userInfo, token);
        // 登录成功, 移除Code
        WAIT_LOGIN_MAP.invalidate(code);
    }

    @Resource
    RedisSessionRepository redisSessionRepository;

    @Override
    public void authorize(Channel channel, String data) {
        Session session = SessionUtil.findSessionBySessionId(data);
        if (!Objects.isNull(session)) {
            Object attribute = session.getAttribute(USER_LOGIN_STATE);
            if (!Objects.isNull(attribute)) {
                WebSocketAdepter.sendAuthorizeSuccessMsg(channel, (User) attribute);
            }
        } else {

        }
    }


    private Integer generateLoginCode(Channel channel) {
        Integer code = null;
        // 生成随机标识, 并必然不重复
        do {
            code = RandomUtil.randomInt(Integer.MAX_VALUE);
        } while (!Objects.isNull(WAIT_LOGIN_MAP.asMap().putIfAbsent(code, channel)));
        return code;
    }

}
