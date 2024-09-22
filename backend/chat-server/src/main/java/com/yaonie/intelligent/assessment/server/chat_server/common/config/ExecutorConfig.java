package com.yaonie.intelligent.assessment.server.chat_server.common.config;


import com.yaonie.intelligent.assessment.server.chat_server.common.thread.CustomThreadFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-17 12:10
 * @Author 武春利
 * @CreateTime 2024-09-17
 * @ClassName ExecutorConfig
 * @Project backend
 * @Description : TODO
 */
@Configuration
@EnableAsync
public class ExecutorConfig implements AsyncConfigurer {
    /**
     * 项目公用线程池名称
     */
    public static final String CHAT_EXECUTOR = "chatExecutor";

    /**
     * websocket通信线程池名称
     */
    public static final String WEBSOCKET_EXECUTOR = "websocketExecutor";

    @Override
    public Executor getAsyncExecutor() {
        return chatExecutor();
    }

    @Bean(CHAT_EXECUTOR)
    @Primary
    public ThreadPoolTaskExecutor chatExecutor() {
        ThreadPoolTaskExecutor chatExecutor = new ThreadPoolTaskExecutor();
        // 设置线程工厂,
        // ThreadPoolTaskExecutor 默认使用的是 java.util.concurrent.Executors.defaultThreadFactory()
        // 它本身就是一个线程工厂
        chatExecutor.setThreadFactory(new CustomThreadFactory(chatExecutor));
        // 线程池关闭的时候等待 所有任务都完成 再继续销毁其他的Bean
        // 线程池优雅停机的关键
        chatExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 核心线程数
        chatExecutor.setCorePoolSize(10);
        // 最大线程数
        chatExecutor.setMaxPoolSize(20);
        // 队列容量
        chatExecutor.setQueueCapacity(200);
        // 线程名称前缀
        chatExecutor.setThreadNamePrefix("chatExecutor-");
        // 拒绝策略 (队列满了, 用当前线程进行处理)
        chatExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 线程池初始化
        chatExecutor.initialize();
        return chatExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }
}
