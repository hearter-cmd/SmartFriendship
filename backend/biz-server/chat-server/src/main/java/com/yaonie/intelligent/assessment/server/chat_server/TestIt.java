package com.yaonie.intelligent.assessment.server.chat_server;


import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.FriendMessage;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.Message;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import com.yaonie.intelligent.assessment.server.common.util.JsonUtils;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.concurrent.ExecutionException;


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
    private RedissonClient redissonClient;

    @Resource
    private WebSocketService webSocketService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    void test1() {
//        RLock lock = redissonClient.getLock("lock");
//        lock.lock();
//        User userByMpOpenId = userService.getUserByMpOpenId("1");
//        System.out.println(userByMpOpenId);
//        lock.unlock();
        FriendMessage friendMessage = new FriendMessage();
        friendMessage.setFromUserId(0L);
        friendMessage.setToUserId(1834256123482370058L);
        friendMessage.setMessage("测试一下");
        rabbitTemplate.convertAndSend("chat.message.routing.key.friend", friendMessage);
    }

    @Test
    void test2() {
        Message message = new Message();
        message.setContactId(0L);
        message.setUserId(0L);
        message.setMessage("测试一下");
        message.setType(0);
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());
//        webSocketService.handleMsg();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOjE4MzQyNTYxMjM0ODIzNzAwNTMsImNyZWF0ZVRpbWUiOjE3MjY1MTU1MTZ9.TYvFJKZ2K1Gp3N9vpf6_vhl0grKFm1Ji7O8H3e7ZDbw";
//        System.out.println(JwtUtil.getUidOrNull(token));

        //region 测试SESSION加密
        String sessionId = "ec83a43e-2375-4f55-918a-8544b864fd90";
//        byte[] decodedBytes = Base64.getDecoder().decode(sessionId);;
//        System.out.println("decodedBytes = " + new String(decodedBytes));
//        byte[] encode = Base64.getEncoder().encode(sessionId.getBytes());
//        System.out.println("encode = " + new String(encode));
        //endregion
        UserVO userVO = new UserVO();
        userVO.setId(1834256123482370058L);
        userVO.setUserName("");
        userVO.setUserAvatar("");
        userVO.setUserProfile("");
        userVO.setUserRole("");
        userVO.setCreateTime(new Date());
        userVO.setSession("");
        System.out.println(JsonUtils.toStr(userVO));

//        System.out.println(IpUtil.getIpAreaByGaoDe("106.119.59.252"));
    }
}
