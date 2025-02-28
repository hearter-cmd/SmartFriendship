package com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums;


import lombok.Getter;

/**
 * @author 77160
 */

@Getter
public enum DateTimePatternEnum {
    YYYY_MM_DD_HH_MM_SS("yyyy-MM-dd HH:mm:ss"), YYYY_MM_DD("yyyy-MM-dd");

    private final String pattern;

    DateTimePatternEnum(String pattern) {
        this.pattern = pattern;
    }

}
