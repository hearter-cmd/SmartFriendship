package com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 19:00
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WxEventEnum
 * @Project backend
 * @Description : 
 */
@AllArgsConstructor
@Getter
public enum WxEventEnum {
    SCAN("SCAN","扫描带参数二维码事件"),
    LOCATION("LOCATION","上报地理位置事件"),
    CLICK("CLICK","点击菜单拉取消息时的事件推送"),
    VIEW("VIEW","点击菜单跳转链接时的事件推送"),
    SUBSCRIBE("subscribe","订阅事件"),
    UNSUBSCRIBE("unsubscribe","取消订阅事件"),
    TEMPLATESENDJOBFINISH("TEMPLATESENDJOBFINISH","模板消息发送完成事件"),
    ;

    private final String code;
    private final String desc;
}
