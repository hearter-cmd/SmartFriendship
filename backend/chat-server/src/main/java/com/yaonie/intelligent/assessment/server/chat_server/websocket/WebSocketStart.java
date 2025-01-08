package com.yaonie.intelligent.assessment.server.chat_server.websocket;


import com.yaonie.intelligent.assessment.server.chat_server.websocket.handler.CustomHttpRequestHandler;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.handler.NettyWebSocketServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.Future;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-24 15:07
 * @Description : WebSocket启动类
 */
@Configuration
@Slf4j
public class WebSocketStart {
    /*
    因为我们处理业务, 不婚存储数据, 只是对数据进行处理
    不会造成业务或数据的冲突, 直接使用一个处理器就可以了
     */
    public static final NettyWebSocketServerHandler NETTY_WEB_SOCKET_SERVER_HANDLER = new NettyWebSocketServerHandler();
    @Value("${ws.port}")
    private static final int PORT = 8105;
    /*
    1. 创建两个处理线程池
       1.1 默认为IO密集型线程池
          boss队列 也就是服务器进行监听的队列
          这里设置为 1 , 只有一个中转
    */
    private static final EventLoopGroup BOSS_GROUP = new NioEventLoopGroup(1);
    /**
     * 1.2 工作队列 CPU密集型
     *        根据可用CPU核心设置工作线程
     */
    private static final EventLoopGroup WORKER_GROUP = new NioEventLoopGroup(NettyRuntime.availableProcessors());

    @PostConstruct
    public void run() {
        /*
        2. 创建Netty的启动器
            2.1 划分组
            2.2 选择通道的类型
            2.3 添加一个日志处理器, 方便打印日志, 等级为INFO
            2.4 将自定义的处理器添加到Netty的工作线程
            2.5 设置夫Channel套接字选项
            2.6 设置子Channel套接字选项
            2.7 绑定端口, 并以异步运行
         */
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap
                    .group(BOSS_GROUP, WORKER_GROUP)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加心跳
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            // 添加Http编解码器
                            pipeline.addLast(new HttpServerCodec());
                            //
                            pipeline.addLast(new HttpObjectAggregator(65535));
                            // 自定义Http请求处理器
                            pipeline.addLast(new CustomHttpRequestHandler());
                            pipeline.addLast(NETTY_WEB_SOCKET_SERVER_HANDLER);
                        }
                    });
            // 3.同步运行, 并且监听返回值
            serverBootstrap.bind(PORT).sync();

            log.info("WebSocket服务启动成功~~~");
            /*
            4. 优雅的 关闭服务中的线程, 等待直到服务关闭
            关闭服务, 在业务中用不到
            sync.channel().closeFuture().sync();
             */
        } catch (InterruptedException e) {
            // 处理中断异常
            System.out.println("WebSocket服务启动失败" + "-error:" + e.getLocalizedMessage());
        }
    }

    /**
     * 优雅停机, 销毁关闭
     */
    @PreDestroy
    public void destory() {
        /*
         * 5. 释放两个线程组, 启动器的作用仅仅是启动
         *   WORKER_GROUP.shutdownGracefully();
         *   WORKER_GROUP.shutdownGracefully();
         */
        Future<?> future = WORKER_GROUP.shutdownGracefully();
        Future<?> future1 = WORKER_GROUP.shutdownGracefully();
        future.syncUninterruptibly();
        future1.syncUninterruptibly();
        log.info("WebSocket服务器关闭成功");
    }
}
