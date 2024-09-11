package com.yaonie.intelligent_assessment_server.search_server;


import cn.hutool.http.HttpRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Map;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 14:52
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName CrawlerTest
 * @Project backend
 * @Description : TODO
 */
@SpringBootTest
public class CrawlerTest {
    @Resource
    Map<String, CeShi> map;

    @Test
    void test1(){
        for (String s : map.keySet()) {
            System.out.println(s);
        }
    }
}
