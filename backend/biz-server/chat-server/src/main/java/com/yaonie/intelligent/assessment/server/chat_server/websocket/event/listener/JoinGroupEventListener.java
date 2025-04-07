package com.yaonie.intelligent.assessment.server.chat_server.websocket.event.listener;


import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import com.yaonie.intelligent.assessment.server.common.model.event.JoinGroupEvent;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupMember;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-31
 */
@Component
public class JoinGroupEventListener {
    @Resource
    private WebSocketService webSocketService;

    @EventListener(JoinGroupEvent.class)
    public void handleJoinGroupEvent(JoinGroupEvent event) {
        GroupMember groupMember = event.getGroupMember();
        Long userId = groupMember.getUserId();
        Long groupId = groupMember.getGroupId();
        webSocketService.joinGroup(userId, groupId);
    }
}
