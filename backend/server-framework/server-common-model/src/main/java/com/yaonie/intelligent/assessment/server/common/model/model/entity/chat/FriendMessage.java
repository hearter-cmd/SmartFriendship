package com.yaonie.intelligent.assessment.server.common.model.model.entity.chat;


import lombok.Data;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-24 16:51
 * @Description : 好友消息
 */
@Data
public class FriendMessage {
    private Long fromUserId;
    private Long toUserId;
    private String message;
    private String avatar;
}
