package com.yaonie.intelligent.assessment.server.springbootinit.config;


import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-29 0:17
 * @Author 武春利
 * @CreateTime 2024-08-29
 * @ClassName RedissionConfig
 * @Project backend
 * @Description : TODO
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedissonConfig {
    private static final String SUFFIX = "redis://";
    private String host;
    private String port;
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(RedissonConfig.SUFFIX + host + ":" + port)
                .setDatabase(8)
                .setPassword(password);
        return Redisson.create(config);
    }
}
