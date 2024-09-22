package com.yaonie.intelligent.assessment.server.chat_server.websocket.adepter;


import cn.hutool.core.bean.BeanUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums.WSRespTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSBaseResponse;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp.WSSetSession;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.utils.NettyUtil;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import com.yaonie.intelligent.assessment.server.common.util.JsonUtils;
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
        return new TextWebSocketFrame(JsonUtils.toStr(response));
    }

    public static <T> void sendWSTextMsg(Channel channel, WSRespTypeEnum type, T msg) {
        TextWebSocketFrame textWebSocketFrame = WebSocketAdepter.buildWSText(type, msg);
        channel.writeAndFlush(textWebSocketFrame);
    }

    public static void sendWSLoginSuccessMsg(Channel channel, User userInfo) {
        UserVO loginSuccess = fillUserInfo(userInfo, channel);
        sendWSTextMsg(channel, WSRespTypeEnum.LOGIN_SUCCESS, loginSuccess);
    }

    public static void sendUnAuthorizeSuccessMsg(Channel channel, String errorMessage) {
        sendWSTextMsg(channel, WSRespTypeEnum.INVALIDATE_TOKEN, errorMessage);
    }

    private static UserVO fillUserInfo(User userInfo, Channel channel) {
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(userInfo, userVO);
        userVO.setSession(NettyUtil.getChannelAttr(channel, NettyUtil.TypeEnum.SESSION_ID));
        return userVO;
    }

    public static void sendNewSessionId(Channel channel, String sessionId) {
        sendWSTextMsg(channel, WSRespTypeEnum.SET_SESSION, new WSSetSession(sessionId));
    }
}
