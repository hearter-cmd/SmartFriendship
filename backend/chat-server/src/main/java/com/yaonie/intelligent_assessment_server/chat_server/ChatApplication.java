package com.yaonie.intelligent_assessment_server.chat_server;


import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-30 19:33
 * @Author 武春利
 * @CreateTime 2024-08-30
 * @ClassName ChatApplicaton
 * @Project backend
 * @Description : TODO
 */
// 开启异步
@EnableAsync
// 开启事务
@EnableTransactionManagement
// 扫描maer
@MapperScan("com.yaonie.intelligent_assessment_server.chat_server.mappers")
@SpringBootApplication
public class ChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }
}
