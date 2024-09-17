package com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums;


import lombok.Getter;

import java.util.Arrays;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-13 12:55
 * @Author 武春利
 * @CreateTime 2024-09-13
 * @ClassName UserContactEnum
 * @Project backend
 * @Description : 用户好友状态枚举
 */
@Getter
public enum UserContactStatusEnum {
    NOT_FRIEND(0, "非好友"),
    FRIEND(1, "好友"),
    DEL(2, "已删除好友"),
    DEL_BE(3, "被好友删除"),
    BLACKLIST(4, "已拉黑好友"),
    BLACKLIST_BE(5, "被好友拉黑");

    private final Integer status;
    private final String desc;

    UserContactStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public static UserContactStatusEnum getByStatus(String statusStr) {
        try {
            if (statusStr == null || statusStr.isEmpty()) {
                return null;
            }
            // 注意：这里我们不能直接使用valueOf，因为valueOf期望的是枚举的名称，而不是状态码
            // 我们需要自定义逻辑来匹配状态码
            return Arrays.stream(UserContactStatusEnum.values())
                    .filter(e -> e.status.equals(Integer.parseInt(statusStr)))
                    .findFirst()
                    .orElse(null);
        } catch (NumberFormatException e) {
            // 如果输入的不是有效的整数，则返回null
            return null;
        }
    }
}