package com.yaonie.intelligent.assessment.server.chat_server.websocket.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.yaonie.intelligent.assessment.server.chat_server.utils.SessionUtil;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisSessionRepository;

import static com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant.USER_LOGIN_STATE;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-17 16:19
 * @Author 武春利
 * @CreateTime 2024-09-17
 * @ClassName CustomHttpRequestHandler
 * @Project backend
 * @Description : TODO
 */
@Slf4j
public class CustomHttpRequestHandler extends SimpleChannelInboundHandler<HttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
        HttpHeaders headers = msg.headers();
        Session session = SessionUtil.findSessionByHeaders(headers);
        if (session != null) {
            User currentUser = session.getAttribute(USER_LOGIN_STATE);
            log.info("userObj:{}, sessionId:{}", currentUser, session.getId());
        }
        ReferenceCountUtil.retain(msg);
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("Exception caught in CustomHttpRequestHandler", cause);
    }
}
