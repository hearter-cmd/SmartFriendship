package com.yaonie.intelligent.assessment.server.chat_server;

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
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.NettyRuntime;
import io.netty.util.concurrent.Future;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 * @Author 武春利
 * @CreateTime 2024-08-30 19:33
 * @Description : 聊天主模块
 */
@Slf4j
// 开启异步
@EnableAsync
// 开启AOP
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
// 开启分布式Session
@EnableRedisHttpSession
// 开启事务
@EnableTransactionManagement
@MapperScan("com.yaonie.intelligent.assessment.server.chat_server.user.mappers")
@SpringBootApplication(scanBasePackages = "com.yaonie.intelligent.assessment.server")
@EnableFeignClients(basePackages = {"com.yaonie.intelligent.assessment.feign.evaluation"})
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    private static final int PORT = 8105;
    /*
    因为我们处理业务, 不婚存储数据, 只是对数据进行处理
    不会造成业务或数据的冲突, 直接使用一个处理器就可以了
     */
    public static final NettyWebSocketServerHandler NETTY_WEB_SOCKET_SERVER_HANDLER = new NettyWebSocketServerHandler();
    /*
    1. 创建两个处理线程池
       1.1 默认为IO密集型线程池
          boss队列 也就是服务器进行监听的队列
          这里设置为 1 , 只有一个中转
    */
    private static EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    /* 1.2 工作队列 CPU密集型
             根据可用CPU核心设置工作线程
     */
    private static EventLoopGroup workerGroup = new NioEventLoopGroup(NettyRuntime.availableProcessors());

    @PostConstruct
    public void run() throws Exception {
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
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加心跳
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.MINUTES));
                            // 添加Http编解码器
                            pipeline.addLast(new HttpServerCodec());
                            //
                            pipeline.addLast(new HttpObjectAggregator(65535));
                            // 自定义Http请求处理器
                            pipeline.addLast(new CustomHttpRequestHandler());
                            pipeline.addLast(NETTY_WEB_SOCKET_SERVER_HANDLER);
                        }
                    });
            // 3.异步运行, 并且监听返回值
            ChannelFuture sync = serverBootstrap.bind(PORT).sync();

            log.info("WebSocket服务启动成功~~~");
            /*
            4. 优雅的 关闭服务中的线程, 等待直到服务关闭
            关闭服务, 在业务中用不到
            sync.channel().closeFuture().sync();
             */
        } catch (InterruptedException e) {
            // 处理中断异常
            e.printStackTrace();
        } finally {
            /*
            5. 释放两个线程组, 启动器的作用仅仅是启动
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
             */
        }
    }

    /**
     * 优雅停机, 销毁关闭
     */
    @PreDestroy
    public void destory(){
        Future<?> future = bossGroup.shutdownGracefully();
        Future<?> future1 = workerGroup.shutdownGracefully();
        future.syncUninterruptibly();
        future1.syncUninterruptibly();
        log.info("WebSocket服务器关闭成功");
    }
}
