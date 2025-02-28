package com.yaonie.intelligent.assessment.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 指定Key的序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // 指定Value的序列化方式
        redisTemplate.setValueSerializer(RedisSerializer.json());
        // 设置Hash的序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // 设置Hash的Value的序列化方式
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}