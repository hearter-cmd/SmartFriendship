package com.yaonie.intelligent.assessment.server.search_server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.config.EnableElasticsearchAuditing;
import org.springframework.data.elasticsearch.config.EnableReactiveElasticsearchAuditing;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 14:51
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName SearchApplication
 * @Project backend
 * @Description :
 */
@SpringBootApplication(scanBasePackages = {
        "com.yaonie.intelligent.assessment.server"
})
@EnableElasticsearchAuditing
@EnableReactiveElasticsearchAuditing
//@EnableElasticsearchRepositories
//@EnableReactiveElasticsearchRepositories
public class SearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchApplication.class, args);
    }
}
