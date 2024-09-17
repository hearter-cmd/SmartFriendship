package com.yaonie.intelligent.assessment.server.springbootinit.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-12 1:32
 * @Author 武春利
 * @CreateTime 2024-09-12
 * @ClassName BeanConfig
 * @Project backend
 * @Description : 
 */
@Configuration
public class BeanConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
