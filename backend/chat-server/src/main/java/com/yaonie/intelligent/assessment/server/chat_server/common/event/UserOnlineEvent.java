package com.yaonie.intelligent.assessment.server.chat_server.common.event;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-18 16:48
 * @Author 武春利
 * @CreateTime 2024-09-18
 * @ClassName UserOnlineEvent
 * @Project backend
 * @Description : 用户上线事件
 */
@Getter
public class UserOnlineEvent extends ApplicationEvent {
    private final User user;

    public UserOnlineEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
