package com.yaonie.intelligent.assessment.server.search_server.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 20:12
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName MybatisConfig
 * @Project backend
 * @Description :
 */
@Configuration
@MapperScan(basePackages = {"com.yaonie.intelligent.assessment.server.search_server.mapper"})
public class MybatisConfig {
}
