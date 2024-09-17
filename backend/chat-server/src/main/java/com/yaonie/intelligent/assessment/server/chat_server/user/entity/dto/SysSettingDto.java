package com.yaonie.intelligent.assessment.server.chat_server.user.entity.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yaonie.intelligent.assessment.server.chat_server.constants.Constants;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-13 2:24
 * @Author 武春利
 * @CreateTime 2024-09-13
 * @ClassName SysSettingDto
 * @Project backend
 * @Description : 系统设置DTO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Component
public class SysSettingDto implements Serializable {
    /**
     * 最大群组数量
     */
    private Integer maxGroupCount = 5;

    /**
     * 最大群成员数量
     */
    private Integer maxGroupMemberCount = 500;

    /**
     * 最大好友数量
     */
    private Integer maxImageSize = 2;

    /**
     * 最大视频大小
     */
    private Integer maxVideoSize = 5;

    /**
     * 最大文件大小
     */
    private Integer maxFileSize = 5;

    /**
     * 聊天机器人ID
     */
    private Long robotUid = Constants.ROBOT_UID;

    /**
     * 聊天机器人昵称
     */
    private String robotNickName = "Friend";

    /**
     * 欢迎语
     */
    private String robotWelcome = "欢迎使用聊天系统";

    @Serial
    private static final long serialVersionUID = 1L;
}
