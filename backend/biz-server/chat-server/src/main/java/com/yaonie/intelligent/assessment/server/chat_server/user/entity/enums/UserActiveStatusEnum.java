package com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 77160
 */

@AllArgsConstructor
@Getter
public enum UserActiveStatusEnum {
    ONLINE(1, "在线"),
    OFFLINE(2, "离线");

    private final Integer type;
    private final String desc;
}