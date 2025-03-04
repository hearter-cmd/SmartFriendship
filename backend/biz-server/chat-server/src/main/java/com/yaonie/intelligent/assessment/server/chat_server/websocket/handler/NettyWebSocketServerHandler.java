package com.yaonie.intelligent.assessment.server.chat_server.websocket.handler;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums.WSReqTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.req.WSBaseRequest;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 0:44
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName BaseServerHandler
 * @Project backend
 * @Description : 
 */
// 这个注解保证了, 不会被重复创建, 只使用一个共享的处理器
@Slf4j
@ChannelHandler.Sharable
public class NettyWebSocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private WebSocketService webSocketService;

    /**
     * 当前ws服务器被连接一次就执行一次这个方法, 只在刚刚建立连接的时候调用
     * @param ctx 通道处理器上下文
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        webSocketService = SpringUtil.getBean(WebSocketService.class);
        webSocketService.addChannel(ctx.channel());
    }

    /**
     * 用户事件监控
     * @param ctx Handler上下文
     * @param evt 对应的事件
     * @throws Exception 报错
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            // 握手完成事件
            log.info("用户连接建立~~~");
        } else if (evt instanceof IdleStateEvent event) {
            // 空闲事件, 强转之后才能调用API
            /*
                IdleState.READER_IDLE 读空闲事件
                IdleState.WRITER_IDLE 写空闲事件
                IdleState.ALL_IDLE 读写空闲事件
             */
            if (event.state() == IdleState.READER_IDLE) {
                log.info("用户读空闲~~~");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("用户写空闲~~~");
            }
            //  用户下线
            //  30秒内用户未发送到消息, 就进行下线
            ctx.channel().close();
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    /**
     * 用户下线
     * @param ctx 上下文
     * @throws Exception 报错
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        userOffline(ctx.channel());

        log.info("用户已离线");
        super.channelInactive(ctx);
    }

    /**
     * 读取数据
     * @param ctx 上下文, 也是处理器链
     * @param msg 消息
     * @throws Exception 报错
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        if ("ping".equals(text)) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame("pong"));
            return;
        }
        WSBaseRequest request = JSONUtil.toBean(text, WSBaseRequest.class);
        WSReqTypeEnum reqType = WSReqTypeEnum.getByType(request.getType());
        switch (Objects.requireNonNull(reqType)) {
            case LOGIN : {
                // 执行登录请求, 等待用户扫码登录
                webSocketService.handleWxUserLogin(ctx.channel());
            }break;
            case AUTHORIZE:{
                // 登录认证返回的data就是token
                webSocketService.authorize(ctx.channel());
            }break;
            case HEARTBEAT: {

            }break;
            default: {
                log.info("未知请求类型");
            }break;
        }
        System.out.println(text);
//        super.channelRead(ctx, msg);
    }

    private void userOffline(Channel channel) {
        // 用户下线
        //  1. 从在线用户列表中移除
        webSocketService.remove(channel);
        //  - > 2. 通知其他用户, 用户下线
        //  3. 关闭连接
        channel.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("NettyWebSocketServerHandler exceptionCaught: {}", cause.getMessage(), cause);
    }
}
