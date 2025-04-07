package com.yaonie.intelligent.assessment.server.springbootinit.config;


import com.yaonie.intelligent.assessment.server.springbootinit.properties.ZhiPuAi;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-22 15:16
 * @Author 武春利
 * @CreateTime 2024-08-22
 * @ClassName AiConfig
 * @Project backend
 * @Description : AI 配置合集
 */
@Configuration
@ConditionalOnProperty(prefix = "ai", name = "tag", havingValue = "true")
@Data
public class AiConfig {
    @Autowired(required = false)
    private ZhiPuAi zhiPuAi;
}
