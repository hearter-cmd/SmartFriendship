package com.yaonie.intelligent_assessment_server.search_server.config;


import com.yaonie.intelligent_assessment_server.search_server.aop.AuthInterceptor;
import com.yaonie.intelligent_assessment_server.search_server.aop.LogInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-08 1:20
 * @Author 武春利
 * @CreateTime 2024-09-08
 * @ClassName MvcConfig
 * @Project backend
 * @Description : TODO
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Resource
    private LogInterceptor logInterceptor;

    @Resource
    private AuthInterceptor authInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 覆盖所有请求
        registry.addMapping("/**")
                // 允许发送 Cookie
                .allowCredentials(true)
                // 放行哪些域名（必须用 patterns，否则 * 会和 allowCredentials 冲突）
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
