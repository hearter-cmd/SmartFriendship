package com.yaonie.intelligent.assessment.server.chat_server.websocket.event;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-23 0:41
 * @Description : 用户不活跃事件
 */
@Getter
public class UserInactiveEvent extends ApplicationEvent {
    private final Long userId;

    public UserInactiveEvent(Object source, Long userId) {
        super(source);
        this.userId = userId;
    }
}
