package com.yaonie.intelligent.assessment.server.chat_server.user.entity.query;


import lombok.Getter;
import lombok.Setter;

/**
 * 联系人关系表参数
 *
 * @author 77160
 */
@Setter
@Getter
public class UserContactQuery extends BaseParam {


    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 其他用户ID 或 群组ID
     */
    private String contactId;

    private String contactIdFuzzy;

    /**
     * 联系人类型 (0 : 好友 1 : 群组)
     */
    private Integer contactType;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 状态 0:非好友 1:好友 2:已删除好友 3:被好友删除 4:已拉黑好友 5:被好友拉黑
     */
    private Integer status;


}
