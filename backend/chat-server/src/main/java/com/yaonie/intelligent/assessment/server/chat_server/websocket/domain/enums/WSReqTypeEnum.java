package com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 14:05
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WSReqTypeEnum
 * @Project backend
 * @Description :
 *          WS请求类型枚举
 */
@AllArgsConstructor
@Getter
public enum WSReqTypeEnum {
    LOGIN(1, "请求登录二维码"),
    HEARTBEAT(2, "心跳包"),
    AUTHORIZE(3, "登录认证");

    // 定义了两个私有成员变量
    private final Integer type;
    private final String desc;

    public static WSReqTypeEnum getByType(Integer type) {
        for (WSReqTypeEnum value : values()) {
            if (Objects.equals(value.getType(), type)) {
                return value;
            }
        }
        return null;
    }
}
