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
public class RegisterEvent extends ApplicationEvent {
    private final String userId;
    private final String sex;
    private final String areaName;
    private final List<String> tags;

    public RegisterEvent(String userId, String sex, String areaName, List<String> tags, Object source) {
        super(source);
        this.userId = userId;
        this.sex = sex;
        this.areaName = areaName;
        this.tags = tags;
    }
}
