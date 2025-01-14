package com.yaonie.intelligent.assessment.feign.config;

import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * @author 77160
 */
/**
 * <p>
 *  feign透传header
 * </p>
 * @author example
 * @date 2021-05-26 22:16
 */
@Configuration
@Slf4j
public class FeignConfig implements RequestInterceptor {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                String values = request.getHeader(name);

                // 跳过 content-length，解决too many bites written的问题
                if ("content-length".equalsIgnoreCase(name)){
                    continue;
                }
                template.header(name, values);
            }
            log.info("feign interceptor header:{}",template);
        }
    }
}