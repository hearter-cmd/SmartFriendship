package com.yaonie.intelligent.assessment.server.chat_server.chat.event.listener;


import com.yaonie.intelligent.assessment.server.chat_server.chat.event.SendMessageEvent;
import com.yaonie.intelligent.assessment.server.chat_server.chat.service.MessageService;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.Message;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 21:38
 * @Description : 发送消息事件监听器
 */
@Component
public class SendMessageListener {

    @Resource
    private WebSocketService webSocketService;

    @Resource
    private MessageService messageService;

    /**
     * 发送消息
     *
     * @param event 发送消息事件
     */
    @EventListener(classes = {SendMessageEvent.class})
    public void onSendFriendMessage(SendMessageEvent event) {
        Message message = event.getMessage();
        // 1. 发送消息
        Long contactId = message.getContactId();
        switch (Objects.requireNonNull(
                UserContactTypeEnum.getEnumByLen(contactId)
        )) {
            case USER:
                messageService.sendToUser(message);
                break;
            case GROUP:
                messageService.sendToGroup(message);
                break;
            default:
                System.out.println(message);
        }
    }

    /**
     * 持久化消息
     *
     * @param event 发送消息事件
     */
    @EventListener(classes = SendMessageEvent.class)
    public void onSendGroupMessage(SendMessageEvent event) {
        // 2. 持久化
        Message message = event.getMessage();
        messageService.save(message);
        // 处理发送消息事件
        System.out.println("发送消息事件监听器2：" + event.getMessage());
    }

}
