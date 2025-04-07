package com.yaonie.intelligent.assessment.ai.event.listener;


import com.yaonie.intelligent.assessment.ai.service.GroupVectorService;
import com.yaonie.intelligent.assessment.server.common.model.event.CreateGroupEvent;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-04-05
 */
@Component
public class CreateGroupListener {
    @Resource
    private GroupVectorService groupVectorService;

    @EventListener(classes = {CreateGroupEvent.class})
    public void handleCreateGroupEvent(CreateGroupEvent createGroupEvent) {
        GroupInfo groupInfo = createGroupEvent.getGroupInfo();
        groupVectorService.insert(String.valueOf(groupInfo.getGroupId()), List.of(groupInfo.getTags().split(",")));
    }
}
