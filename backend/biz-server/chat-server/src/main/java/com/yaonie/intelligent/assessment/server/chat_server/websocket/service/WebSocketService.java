package com.yaonie.intelligent.assessment.server.chat_server.websocket.service;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.Message;
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
    /**
     * 添加通道
     *
     * @param channel websocket通道
     */
    void addChannel(Channel channel);

    /**
     * 处理微信用户登录
     *
     * @param channel websocket通道
     * @throws WxErrorException 微信异常
     */
    void handleWxUserLogin(Channel channel) throws WxErrorException;

    /**
     * 移除通道
     *
     * @param channel websocket通道
     */
    void remove(Channel channel);

    /**
     * 用户登录成功的逻辑
     *
     * @param code   事件码
     * @param openid 用户openid
     */
    void scanLoginSuccess(Integer code, Long openid);

    /**
     * 验证用户身份
     *
     * @param channel websocket通道
     */
    void authorize(Channel channel);

    /**
     * 用户登录成功的逻辑
     *
     * @param channel  websocket通道
     * @param userInfo 用户信息
     * @param session  session
     */
    void loginSuccess(Channel channel, User userInfo, Session session);

    /**
     * 发送消息
     *
     * @param message 消息
     */
    void handleMsg(Message message);

    void joinGroup(Long userId, Long groupId);

    /**
     *
     */
}
