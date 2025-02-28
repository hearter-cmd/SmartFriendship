package com.yaonie.intelligent.assessment.server.chat_server.user.controller;


import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.Message;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 23:33
 * @Description : 测试控制器
 */
@RestController
@RequestMapping("/doSend")
public class TestController {

    @Resource
    private WebSocketService webSocketService;

    @GetMapping("test")
    public void test(){
        Message message = new Message();
        message.setContactId(1834256123482370058L);
        message.setUserId(0L);
        message.setMessage("测试一下");
        message.setType(4);
        message.setCreateTime(new Date());
        message.setUpdateTime(new Date());
        webSocketService.handleMsg(message);
    }
}
