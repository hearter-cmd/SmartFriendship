package com.yaonie.intelligent_assessment_server.search_server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 14:51
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName SearchApplication
 * @Project backend
 * @Description : TODO
 */
@SpringBootApplication(scanBasePackages = {
        "com.yaonie.intelligent_assessment_server"
})
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
