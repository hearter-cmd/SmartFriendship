package com.yaonie.intelligent.assessment.server.chat_server.websocket.event.listener;


import com.yaonie.intelligent.assessment.server.chat_server.websocket.event.UserInactiveEvent;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-23 0:42
 * @Description : 处理用户离线锁遗留的东西
 */
@Component
public class UserInactiveEventListener {
    @Resource
    private WebSocketService webSocketService;

    @Async
    @EventListener(UserInactiveEvent.class)
    public void handleUserInactiveEvent(UserInactiveEvent event) {

    }
}
