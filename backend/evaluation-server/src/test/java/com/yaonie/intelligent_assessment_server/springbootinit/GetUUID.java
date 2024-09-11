package com.yaonie.intelligent_assessment_server.springbootinit;


import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-29 19:54
 * @Author 武春利
 * @CreateTime 2024-08-29
 * @ClassName GetUUID
 * @Project backend
 * @Description : TODO
 */
public class GetUUID {
    @Test
    void test1(){
        String uuid = UUID.randomUUID().toString().replace("-","");
        System.out.println("uuid = " + uuid);
    }
}
