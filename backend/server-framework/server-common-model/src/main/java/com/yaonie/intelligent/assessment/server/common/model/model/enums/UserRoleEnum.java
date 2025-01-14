package com.yaonie.intelligent.assessment.server.common.model.model.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色枚举
 * @author yaonie
 */
@Getter
public enum UserRoleEnum {

    USER("用户", 2L),
    ADMIN("管理员", 1L),
    BAN("被封号", 3L);

    private final String text;

    private final Long value;

    UserRoleEnum(String text, Long value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return List<String> 枚举值列表
     */
    public static List<Long> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 枚举值
     * @return UserRoleEnum 枚举
     */
    public static UserRoleEnum getEnumByValue(Long value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

}
