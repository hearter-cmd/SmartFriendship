package com.yaonie.intelligent.assessment.feign.config;


import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-13 16:05
 * @Author 武春利
 * @CreateTime 2024-09-13
 * @ClassName RetryConfig
 * @Project backend
 * @Description : TODO
 */
@Configuration
public class RetryConfig {
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(
                1000,
                SECONDS.toMillis(1),
                3);
    }
}
