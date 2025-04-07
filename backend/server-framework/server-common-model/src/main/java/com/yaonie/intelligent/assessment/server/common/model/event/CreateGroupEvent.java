package com.yaonie.intelligent.assessment.server.common.model.event;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-04-05
 */
@Getter
public class CreateGroupEvent extends ApplicationEvent {
    private final GroupInfo groupInfo;
    public CreateGroupEvent(GroupInfo groupInfo) {
        super(groupInfo);
        this.groupInfo = groupInfo;
    }
}
