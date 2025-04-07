package com.yaonie.intelligent.assessment.server.chat_server.user.entity.query;


import lombok.Getter;
import lombok.Setter;

/**
 * 群聊存档参数
 *
 * @author 77160
 */
@Setter
@Getter
public class GroupInfoQuery extends BaseParam {


    /**
     * 群聊ID
     */
    private Long groupId;

    /**
     * 群组名
     */
    private String groupName;

    private String groupNameFuzzy;

    /**
     * 群头像链接
     */
    private String groupAvatar;

    private String groupAvatarFuzzy;

    /**
     * 群主ID
     */
    private Long groupOwnerId;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 群公告
     */
    private String groupNotice;

    private String groupNoticeFuzzy;

    /**
     * 加入方式 (0 : 直接加入, 1 : 管理员同意后加入)
     */
    private Integer joinType;

    /**
     * 状态 (1 : 正常 0 : 解散)
     */
    private Integer status;


}
