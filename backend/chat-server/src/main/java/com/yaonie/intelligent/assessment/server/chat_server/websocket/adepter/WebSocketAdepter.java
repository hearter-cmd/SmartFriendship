package com.yaonie.intelligent.assessment.server.chat_server.websocket.adepter;


import cn.hutool.json.JSONUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums.WSRespTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSBaseResponse;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSLoginSuccess;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-16 2:56
 * @Author 武春利
 * @CreateTime 2024-09-16
 * @ClassName WebSocketAdepter
 * @Project backend
 * @Description : 
 */
public class WebSocketAdepter {
    public static <T> WSBaseResponse<T> buildResp(WSRespTypeEnum type, T data) {
        WSBaseResponse<T> response = new WSBaseResponse<>();
        response.setType(type.getType());
        response.setData(data);
        return response;
    }

    public static <T> TextWebSocketFrame buildWSText(WSRespTypeEnum type, T data) {
        WSBaseResponse<T> response = WebSocketAdepter.buildResp(type, data);
        return new TextWebSocketFrame(JSONUtil.toJsonStr(response));
    }

    public static <T> void sendWSTextMsg(Channel channel, WSRespTypeEnum type, T msg) {
        TextWebSocketFrame textWebSocketFrame = WebSocketAdepter.buildWSText(type, msg);
        channel.writeAndFlush(textWebSocketFrame);
    }

    public static void sendWSLoginSuccessMsg(Channel channel, User userInfo, String token) {
        WSLoginSuccess loginSuccess = fillUserInfo(userInfo);
        sendWSTextMsg(channel, WSRespTypeEnum.LOGIN_SUCCESS, loginSuccess);
    }

    public static void sendAuthorizeSuccessMsg(Channel channel, User userInfo) {
        sendWSTextMsg(channel, WSRespTypeEnum.LOGIN_SUCCESS, userInfo);
    }

    private static WSLoginSuccess fillUserInfo(User userInfo) {
        return WSLoginSuccess.builder()
                .userName(userInfo.getUserName())
                .userRole(userInfo.getUserRole())
                .userProfile(userInfo.getUserProfile())
                .userAvatar(userInfo.getUserAvatar())
                .createTime(userInfo.getCreateTime())
                .updateTime(userInfo.getUpdateTime())
                .build();
    }
}
