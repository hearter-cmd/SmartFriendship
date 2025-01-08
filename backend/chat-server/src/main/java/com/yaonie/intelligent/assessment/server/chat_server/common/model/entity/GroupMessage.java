package com.yaonie.intelligent.assessment.server.chat_server.common.model.entity;


import lombok.Data;

import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-24 16:51
 * @Description : TODO
 */
@Data
public class GroupMessage {
    private Long groupId;
    private Long fromUserId;
    private List<Long> toUserIds;
    private String message;
}
