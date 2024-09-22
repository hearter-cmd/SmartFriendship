package com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 14:26
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WSMessage
 * @Project backend
 * @Description : 
 */
public class WSMessage {

    /**
     * 群聊/好友 (发送目标) id
     */
    @SchemaProperty(name = "群聊/好友 (发送目标) id")
    private Long contactId;

    /**
     * 用户Id
     */
    @SchemaProperty(name = "用户Id")
    private Long userId;

    /**
     * 消息主体 (空消息不允许发送)
     */
    @SchemaProperty(name = "消息主体 (空消息不允许发送)")
    private String message;

    /**
     * 目标类型 (群组 , 好友)
     */
    @SchemaProperty(name = "目标类型 (群组 , 好友)")
    private Integer type;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creatTime;
}
