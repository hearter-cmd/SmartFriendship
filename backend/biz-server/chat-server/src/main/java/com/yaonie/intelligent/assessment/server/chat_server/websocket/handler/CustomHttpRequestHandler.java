package com.yaonie.intelligent.assessment.server.chat_server.websocket.handler;


import cn.hutool.extra.spring.SpringUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.adepter.WebSocketAdepter;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.utils.NettyIpUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.utils.NettyUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.utils.SessionUtil;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.session.Session;

import static com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant.USER_LOGIN_STATE;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-17 16:19
 * @Author 武春利
 * @CreateTime 2024-09-17
 * @ClassName CustomHttpRequestHandler
 * @Project backend
 * @Description : 自定义请求升级, 添加其他功能
 */
@Slf4j
public class CustomHttpRequestHandler extends ChannelInboundHandlerAdapter {

    private static WebSocketService webSocketService;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        webSocketService = SpringUtil.getBean(WebSocketService.class);
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpObject httpObject = (HttpObject) msg;
        if (httpObject instanceof HttpRequest) {
            final HttpRequest req = (HttpRequest) httpObject;
            HttpHeaders headers = req.headers();
            headers.get("Cookie");

            //region 自定义IP获取流程
            // 自定义IP获取流程
            String ip = NettyIpUtil.parseForGetIp(ctx, headers);
            NettyUtil.setChannelAttr(ctx, NettyUtil.TypeEnum.IP, ip);
            //endregion

            //region 自定义session获取流程
            String sessionId = SessionUtil.getSessionIdByHeaders(headers);
            if (sessionId == null) {
                sessionId = SessionUtil.createSessionId();
                log.info("channel设置sessionId : {}", sessionId);
                WebSocketAdepter.sendNewSessionId(ctx.channel(), sessionId);
            } else {
                Session session = SessionUtil.findSessionBySessionId(sessionId);
                // SESSION存在, 判断用户是否登录
                if (session != null) {
                    // 通过Session获取用户信息
                    SecurityUser currentUser = session.getAttribute(USER_LOGIN_STATE);
                    // 用户不存在就去登录页面授权
                    if (currentUser != null) {
                        NettyUtil.setChannelAttr(ctx, NettyUtil.TypeEnum.SESSION_ID, sessionId);
                        webSocketService.loginSuccess(ctx.channel(), currentUser.getUser(), session);
                    }
                    log.info("userObj:{}, sessionId:{}", currentUser, session.getId());
                } else {
                    // SESSION不存在, 创建新的SESSION
                    sessionId = SessionUtil.createSessionId();
                    log.info("channel设置sessionId : {}", sessionId);
                }
            }
            // 到这里一定已经登录了
            // 保存SessionID给其他地方用
            NettyUtil.setChannelAttr(ctx, NettyUtil.TypeEnum.SESSION_ID, sessionId);
            //endregion

            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    req.uri(),
                    headers.get("Sec-Websocket-Protocol"),
                    false
            );
            final WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
            if (handshaker == null) {
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else {
                ctx.pipeline().remove(this);
                ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
                handshakeFuture.addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        ctx.fireExceptionCaught(future.cause());
                    } else {
                        ctx.fireUserEventTriggered(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                        ctx.fireUserEventTriggered(new WebSocketServerProtocolHandler.HandshakeComplete(req.uri(), req.headers(), handshaker.selectedSubprotocol()));
                    }
                });
            }
            // 处理机只需要一次
//            ctx.pipeline().remove(this);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught in CustomHttpRequestHandler", cause);
    }
}
