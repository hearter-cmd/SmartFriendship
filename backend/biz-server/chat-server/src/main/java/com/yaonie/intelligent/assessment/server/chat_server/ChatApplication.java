package com.yaonie.intelligent.assessment.server.chat_server;

import cn.hutool.core.thread.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 *
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
@MapperScan(
        {
                "com.yaonie.intelligent.assessment.server.chat_server.user.mappers",
                "com.yaonie.intelligent.assessment.server.chat_server.common.mappers"
        }
)
@SpringBootApplication(scanBasePackages = "com.yaonie.intelligent.assessment.server")
@EnableFeignClients(basePackages = {"com.yaonie.intelligent.assessment.feign.evaluation"})
@EnableRabbit
public class ChatApplication {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5, 10, 1000,
                TimeUnit.MICROSECONDS, new ArrayBlockingQueue<>(100),
                new NamedThreadFactory("test", false),
                new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.execute(() -> {

        });
        SpringApplication.run(ChatApplication.class, args);
    }
}
