package com.yaonie.intelligent.assessment.server.chat_server.websocket.mq.listener;


import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.FriendMessage;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.GroupMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-24 16:43
 * @Description : TODO
 */
@Slf4j
@Component
public class MessageListener {
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "chat.message.queue"),
                    exchange = @Exchange(name = "chat.message.exchange"),
                    key = "chat.message.routing.key.friend",
                    declare = "true"
            )
    })
    public void receiveFriendMessage(FriendMessage message){
        System.out.println("收到消息：" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "chat.message.queue"),
                    exchange = @Exchange(name = "chat.message.exchange"),
                    key = "chat.message.routing.key.friend"
            )
    })
    public void receiveGroupMessage2(GroupMessage message){
        log.info(message.toString());
    }
}
