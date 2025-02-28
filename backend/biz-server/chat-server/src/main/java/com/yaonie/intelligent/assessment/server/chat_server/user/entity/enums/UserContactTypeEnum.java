package com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums;


import io.micrometer.common.util.StringUtils;
import lombok.Getter;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-13 0:40
 * @Author 武春利
 * @CreateTime 2024-09-13
 * @ClassName UserContactTypeEnum
 * @Project backend
 * @Description : 用户申请类型枚举
 */
@Getter
public enum UserContactTypeEnum {
    USER(1, 19, "好友"),
    GROUP(2, 12, "群组"),
    ;

    private final Integer type;
    private final Integer length;
    private final String desc;

    UserContactTypeEnum(Integer type, Integer length, String desc) {
        this.type = type;
        this.length = length;
        this.desc = desc;
    }

    public static UserContactTypeEnum getEnumByType(Integer type) {
        for (UserContactTypeEnum userContactTypeEnum : UserContactTypeEnum.values()) {
            if (userContactTypeEnum.getType().equals(type)) {
                return userContactTypeEnum;
            }
        }
        return null;
    }

    public static UserContactTypeEnum getEnumByLen(Long id) {
        int len = String.valueOf(id).length();
        for (UserContactTypeEnum userContactTypeEnum : UserContactTypeEnum.values()) {
            if (userContactTypeEnum.getLength().equals(len)) {
                return userContactTypeEnum;
            }
        }
        return null;
    }


    public static UserContactTypeEnum getEnumByName(String name) {
        try {
            if (StringUtils.isEmpty((name))) {
                return null;
            }
            return UserContactTypeEnum.valueOf(name.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(UserContactTypeEnum.getEnumByName("GROUP"));
    }
}
