package com.yaonie.intelligent.assessment.server.springbootinit.config;


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
 * @Author 武春利
 * @CreateTime 2024-10-13 19:22
 * @Description : 自定义全局线程池部分, 支持Async使用
 */
@Configuration
@EnableAsync
public class ExecutorConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return bizExecutor();
    }

    @Primary
    @Bean("bizExecutor")
    public ThreadPoolTaskExecutor bizExecutor() {
        ThreadPoolTaskExecutor bizExecutor = new ThreadPoolTaskExecutor();
        bizExecutor.setCorePoolSize(10);
        bizExecutor.setMaxPoolSize(20);
        bizExecutor.setQueueCapacity(100);
        bizExecutor.setThreadNamePrefix("bizExecutor-");
        bizExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        return bizExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }
}
