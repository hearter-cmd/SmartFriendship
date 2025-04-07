package com.yaonie.intelligent.assessment.server.common.model.event;


import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-23
 */
@Getter
public class UpdateTagEvent extends ApplicationEvent {
    private final List<String> tags;

    public UpdateTagEvent(List<String> tags) {
        super(tags);
        this.tags = tags;
    }
}
