package com.yaonie.intelligent.assessment.server.chat_server.websocket.mq.listener;


import com.rabbitmq.client.Channel;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.FriendMessage;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupMessage;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.Message;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-24 16:43
 * @Description : 消息监听器
 */
@Slf4j
@Component
public class MessageListener {

    @Resource
    private WebSocketService webSocketService;

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "chat.message.queue.friend"),
                    exchange = @Exchange(name = "chat.message.exchange"),
                    key = {"chat.message.routing.key.friend"},
                    declare = "true"
            )
    })
    public void receiveFriendMessage(Channel channel, FriendMessage friendMessage) throws IOException {
        Message message = new Message();
        message.setContactId(friendMessage.getToUserId());
        message.setUserId(friendMessage.getFromUserId());
        message.setMessage(friendMessage.getMessage());
        message.setAvatar(friendMessage.getAvatar());
        message.setCreateTime(new Date());
        webSocketService.handleMsg(message);
        System.out.println("收到消息：" + friendMessage);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "chat.message.queue.group"),
                    exchange = @Exchange(name = "chat.group.message.exchange", type = "fanout"),
                    key = {"chat.message.routing.key.group"},
                    declare = "true"
            )
    })
    public void receiveGroupMessage(GroupMessage groupMessage) {
        Message message = new Message();
        message.setContactId(groupMessage.getGroupId());
        message.setUserId(groupMessage.getFromUserId());
        message.setMessage(groupMessage.getMessage());
        message.setCreateTime(new Date());
        message.setAvatar(groupMessage.getAvatar());
        webSocketService.handleMsg(message);
        log.info(groupMessage.toString());
    }
}
