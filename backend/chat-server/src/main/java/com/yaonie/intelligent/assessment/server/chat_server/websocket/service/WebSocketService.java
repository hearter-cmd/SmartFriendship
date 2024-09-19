package com.yaonie.intelligent.assessment.server.chat_server.websocket.service;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import io.netty.channel.Channel;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.session.Session;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-16 0:42
 * @Author 武春利
 * @CreateTime 2024-09-16
 * @ClassName WebSocketService
 * @Project backend
 * @Description : WebSocket服务接口
 */
public interface WebSocketService {
    void addChannel(Channel channel);

    void handleWxUserLogin(Channel channel) throws WxErrorException;

    void remove(Channel channel);

    /**
     * 用户登录成功的逻辑
     * @param code 事件码
     * @param openid 用户openid
     */
    void scanLoginSuccess(Integer code, Long openid);

    void authorize(Channel channel);

    void loginSuccess(Channel channel, User userInfo, Session session);
}
