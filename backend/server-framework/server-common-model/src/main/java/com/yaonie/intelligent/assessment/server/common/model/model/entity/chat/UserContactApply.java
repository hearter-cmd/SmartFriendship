package com.yaonie.intelligent.assessment.server.common.model.model.entity.chat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户人申请表
 *
 * @author 77160
 */
@Setter
@Getter
public class UserContactApply implements Serializable {


    /**
     * 申请id
     */
    @TableId(value = "apply_id", type = IdType.ASSIGN_ID)
    private Integer applyId;

    /**
     * 申请用户id
     */
    @TableField("apply_user_id")
    private String applyUserId;

    /**
     * 申请接收用户id
     */
    @TableField("receive_user_id")
    private String receiveUserId;

    /**
     * 联系人类型 0:好友 1:群聊
     */
    @TableField("concat_type")
    private Integer concatType;

    /**
     * 联系人/群聊id
     */
    @TableField("concat_id")
    private String concatId;

    /**
     * 最后申请时间
     */
    @TableField("last_apply_time")
    private Date lastApplyTime;

    /**
     * 申请状态 0:未处理 1:已同意 2:已拒绝
     */
    private Integer status;

    /**
     * 申请信息
     */
    @TableField("apply_info")
    private String applyInfo;


    @Override
    public String toString() {
        return "申请id:" + (applyId == null ? "空" : applyId) + "，申请用户id:" + (applyUserId == null ? "空" : applyUserId) + "，申请接收用户id:" + (receiveUserId == null ? "空" : receiveUserId) + "，联系人类型 0:好友 1:群聊:" + (concatType == null ? "空" : concatType) + "，联系人/群聊id:" + (concatId == null ? "空" : concatId) + "，最后申请时间:" + (lastApplyTime == null ? "空" : lastApplyTime) + "，申请状态 0:未处理 1:已同意 2:已拒绝:" + (status == null ? "空" : status) + "，申请信息:" + (applyInfo == null ? "空" : applyInfo);
    }
}
