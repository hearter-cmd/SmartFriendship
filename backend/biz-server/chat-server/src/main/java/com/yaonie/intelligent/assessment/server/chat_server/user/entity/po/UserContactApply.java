package com.yaonie.intelligent.assessment.server.chat_server.user.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 用户人申请表
 * @author 77160
 */
@Setter
@Getter
public class UserContactApply implements Serializable {


	/**
	 * 申请id
	 */
	private Integer applyId;

	/**
	 * 申请用户id
	 */
	private Long applyUserId;

	/**
	 * 申请接收用户id
	 */
	private Long receiveUserId;

	/**
	 * 联系人类型 0:好友 1:群聊
	 */
	private Integer concatType;

	/**
	 * 联系人/群聊id
	 */
	private Long concatId;

	/**
	 * 最后申请时间
	 */
	private Long lastApplyTime;

	/**
	 * 申请状态 0:未处理 1:已同意 2:已拒绝
	 */
	private Integer status;

	/**
	 * 申请信息
	 */
	private String applyInfo;


    @Override
	public String toString (){
		return "申请id:"+(applyId == null ? "空" : applyId)+"，申请用户id:"+(applyUserId == null ? "空" : applyUserId)+"，申请接收用户id:"+(receiveUserId == null ? "空" : receiveUserId)+"，联系人类型 0:好友 1:群聊:"+(concatType == null ? "空" : concatType)+"，联系人/群聊id:"+(concatId == null ? "空" : concatId)+"，最后申请时间:"+(lastApplyTime == null ? "空" : lastApplyTime)+"，申请状态 0:未处理 1:已同意 2:已拒绝:"+(status == null ? "空" : status)+"，申请信息:"+(applyInfo == null ? "空" : applyInfo);
	}
}
