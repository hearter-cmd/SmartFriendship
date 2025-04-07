package com.yaonie.intelligent.assessment.server.chat_server.chat.event;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.Message;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 21:39
 * @Description : 发送消息事件
 */
@Getter
public class SendMessageEvent extends ApplicationEvent {
    private final Message message;

    public SendMessageEvent(Object source, Message message) {
        super(source);
        this.message = message;
    }
}
