package com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-30
 */
@Data
public class UserContactApplyVO {
    /**
     * 申请id
     */
    private Integer applyId;

    /**
     * 申请用户
     */
    private UserVO applyUser;

    /**
     * 申请接收用户
     */
    private UserVO receiveUser;

    /**
     * 申请接受群组
     */
    private GroupInfo receiveGroup;

    /**
     * 联系人类型 0:好友 1:群聊
     */
    private Integer concatType;

    /**
     * 联系人/群聊id
     */
    private String concatId;

    /**
     * 最后申请时间
     */
    private Date lastApplyTime;

    /**
     * 申请状态 0:未处理 1:已同意 2:已拒绝
     */
    private Integer status;

    /**
     * 申请信息
     */
    private String applyInfo;


    @Override
    public String toString() {
        return "申请id:" + (applyId == null ? "空" : applyId) + "，申请用户id:" + (applyUser == null ? "空" : applyUser) + "，申请接收用户id:" + (receiveUser == null ? "空" : receiveUser) + "，联系人类型 0:好友 1:群聊:" + (concatType == null ? "空" : concatType) + "，联系人/群聊id:" + (concatId == null ? "空" : concatId) + "，最后申请时间:" + (lastApplyTime == null ? "空" : lastApplyTime) + "，申请状态 0:未处理 1:已同意 2:已拒绝:" + (status == null ? "空" : status) + "，申请信息:" + (applyInfo == null ? "空" : applyInfo);
    }
}
