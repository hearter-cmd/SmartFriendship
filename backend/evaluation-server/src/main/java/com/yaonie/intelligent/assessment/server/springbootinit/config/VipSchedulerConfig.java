package com.yaonie.intelligent.assessment.server.springbootinit.config;



import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r, "scheduler-" + serial.getAndIncrement());
                thread.setDaemon(false);
                return thread;
            }
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20, threadFactory);
        return Schedulers.from(scheduledExecutorService);
    }
}
