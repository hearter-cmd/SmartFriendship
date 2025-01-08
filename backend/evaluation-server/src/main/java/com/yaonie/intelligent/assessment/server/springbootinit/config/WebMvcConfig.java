package com.yaonie.intelligent.assessment.server.springbootinit.config;


import com.yaonie.intelligent.assessment.server.springbootinit.aop.AntiShakeInterceptor;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-01
 */
@Component
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private AntiShakeInterceptor antiShakeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 防抖拦截器
        registry.addInterceptor(antiShakeInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
