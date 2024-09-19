package com.yaonie.intelligent.assessment.server.chat_server.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-11 17:40
 * @Author 武春利
 * @CreateTime 2024-09-11
 * @ClassName RedisConfig
 * @Project backend
 * @Description : 
 */
@Configuration
public class RedisConfig<V> {
    @Bean
    public RedisTemplate<String, V> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 指定Key的序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());
        // 指定Value的序列化方式
        redisTemplate.setValueSerializer(RedisSerializer.json());
        // 设置Hash的序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // 设置Hash的Value的序列化方式
        redisTemplate.setHashValueSerializer(RedisSerializer.json());
        return redisTemplate;
    }
}
