package com.yaonie.intelligent.assessment.server.springbootinit.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-31 15:49
 * @Author 武春利
 * @CreateTime 2024-08-31
 * @ClassName SchedulerConfig
 * @Project backend
 * @Description :
 */
@Configuration
public class VipSchedulerConfig {
    private final AtomicInteger serial = new AtomicInteger(1);

    @Bean
    public Scheduler vipScheduler() {
        // 创建自定义的 ThreadFactory
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Objects.requireNonNull(r, "Runnable cannot be null");
                Thread thread = new Thread(r, "scheduler-" + serial.getAndIncrement());
                thread.setDaemon(false);
                return thread;
            }
        };

        // 使用 Project Reactor 的 Schedulers.newParallel 创建调度器
        return Schedulers.newParallel(20, threadFactory);
    }
}
