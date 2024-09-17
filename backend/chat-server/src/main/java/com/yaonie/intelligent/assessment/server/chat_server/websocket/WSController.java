package com.yaonie.intelligent.assessment.server.chat_server.websocket;


import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-10 11:41
 * @Author 武春利
 * @CreateTime 2024-09-10
 * @ClassName WSController
 * @Project backend
 * @Description : 
 */
@Component
@ServerEndpoint("/ws/chat")
@Slf4j
public class WSController {

    public static ConcurrentHashMap<String, WebSocketSession> chatMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<String, String> groupChatMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(WebSocketSession session) {
        String id = session.getId();
        chatMap.put(id, session);
        log.info("有新连接加入！当前在线人数为{}", chatMap.size());
    }

    /**
     * 关闭连接并删除集合中的连接
     * @param session WebSocketSession
     * @throws IOException IOException
     */
    @OnClose
    public void onClose(WebSocketSession session) throws IOException {
        String id = session.getId();
        WebSocketSession webSocketSession = chatMap.get(id);
        webSocketSession.close();
        chatMap.remove(id);

        log.info("有一连接关闭！当前在线人数为{}", chatMap.size());
    }
}
