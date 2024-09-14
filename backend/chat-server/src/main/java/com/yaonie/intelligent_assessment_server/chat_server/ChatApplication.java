package com.yaonie.intelligent_assessment_server.chat_server;


import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-30 19:33
 * @Author 武春利
 * @CreateTime 2024-08-30
 * @ClassName ChatApplication
 * @Project backend
 * @Description : TODO
 */
// 开启异步
@EnableAsync
// 开启事务
@EnableTransactionManagement
// 扫描mapper
@MapperScan("com.yaonie.intelligent_assessment_server.chat_server.mappers")
@SpringBootApplication(scanBasePackages = "com.yaonie.intelligent_assessment_server")
@EnableFeignClients(basePackages = {
        "com.yaonie.intelligent_assessment_server.feign.evaluation",
})
// 开启AOP
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableRedisHttpSession
public class ChatApplication {
    private int port = 1005;

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    public void start() {
        // 默认为IO密集型线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // 工作队列
        EventLoopGroup workerGroup = new NioEventLoopGroup();
    }
}
