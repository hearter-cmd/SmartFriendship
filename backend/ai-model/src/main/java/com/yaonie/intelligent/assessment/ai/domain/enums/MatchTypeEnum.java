package com.yaonie.intelligent.assessment.ai.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-10
 */
@Getter
@AllArgsConstructor
public enum MatchTypeEnum {
    GROUP("2"),
    FRIEND("1");

    private final String type;
}
