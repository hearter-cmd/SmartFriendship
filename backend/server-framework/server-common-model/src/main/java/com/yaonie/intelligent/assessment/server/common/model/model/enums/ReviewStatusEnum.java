package com.yaonie.intelligent.assessment.server.common.model.model.enums;


import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-18 15:06
 * @Author 武春利
 * @CreateTime 2024-08-18
 * @ClassName ReviewEnum
 * @Project backend
 * @Description : 审核状态枚举
 */
@Getter
public enum ReviewStatusEnum {
    REVIEWING("待审核", 0),
    PASS("审核通过", 1),
    REJECT("拒绝", 2);

    private final String text;
    private final int value;

    ReviewStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 通过状态码获取枚举
     *
     * @param value 状态码
     * @return ReviewStatusEnum 枚举
     */
    public static ReviewStatusEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ReviewStatusEnum anEnum : values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 获取所有的枚举值
     *
     * @return List<Integer> 所有的枚举值列表
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

}
