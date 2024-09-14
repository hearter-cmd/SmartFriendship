package com.yaonie.intelligent_assessment_server.server.gateway;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-13 15:05
 * @Author 武春利
 * @CreateTime 2024-09-13
 * @ClassName GatewayApplication
 * @Project backend
 * @Description : TODO
 */
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
