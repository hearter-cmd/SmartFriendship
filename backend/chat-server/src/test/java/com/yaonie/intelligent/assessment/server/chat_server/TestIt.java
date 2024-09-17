package com.yaonie.intelligent.assessment.server.chat_server;


import com.yaonie.intelligent.assessment.server.chat_server.user.controller.UserController;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserService;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-11 0:25
 * @Author 武春利
 * @CreateTime 2024-09-11
 * @ClassName TestIt
 * @Project backend
 * @Description : 测试类
 */
@SpringBootTest
public class TestIt {
    @Resource
    private UserController userController;

    @Resource
    private UserService userService;

    @Resource
    private RedissonClient redissonClient;

    @Test
    void test1(){
        RLock lock = redissonClient.getLock("lock");
        lock.lock();
        User userByMpOpenId = userService.getUserByMpOpenId("1");
        System.out.println(userByMpOpenId);
        lock.unlock();
    }
}
