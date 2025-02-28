package com.yaonie.intelligent.assessment.server.chat_server.common.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 15:40
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WxMpConfig
 * @Project backend
 * @Description : 
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wx.mp")
public class WxMpProperties {
    /*
     * 是否使用redis
     */
    private boolean useRedis;

    /*
     * redis配置
     */
    private RedisConfig redisConfig;
    @Data
    public static class RedisConfig {
        private String host;
        private int port;
        private int timeout;
        private String password;
        private int database;
    }

    /*
     * 多个公众号配置
     */
    private List<MpConfig> configs;
    @Data
    public static class MpConfig {
        private String appId;
        private String secret;
        private String token;
        private String aesKey;
    }

}
