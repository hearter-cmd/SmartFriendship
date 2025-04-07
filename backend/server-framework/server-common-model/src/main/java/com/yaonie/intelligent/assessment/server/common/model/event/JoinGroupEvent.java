package com.yaonie.intelligent.assessment.server.common.model.event;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupMember;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-31
 */
@Getter
public class JoinGroupEvent extends ApplicationEvent {
    private final GroupMember groupMember;

    public JoinGroupEvent(GroupMember groupMember, GroupMember source) {
        super(source);
        this.groupMember = groupMember;
    }
}
