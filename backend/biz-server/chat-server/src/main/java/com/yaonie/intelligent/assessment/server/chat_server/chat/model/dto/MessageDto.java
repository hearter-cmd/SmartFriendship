package com.yaonie.intelligent.assessment.server.chat_server.chat.model.dto;


import lombok.Data;

import java.util.Date;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 21:31
 * @Description : 发送消息实体类
 */
@Data
public class MessageDto {
    /**
     * 目标ID
     */
    private Long contactId;

    /**
     * 信息主体
     */
    private String message;

    /**
     * 消息类型(0:好友 , 1:群组)
     */
    private String type;

    /**
     * 发送者昵称
     */
    private String userName;

    /**
     * 发送者头像
     */
    private String avatar;

    /**
     * 消息创建时间
     */
    private Date createTime;
}
