package com.yaonie.intelligent.assessment.server.chat_server.user.entity.query;


import lombok.Getter;
import lombok.Setter;

/**
 * 用户人申请表参数
 * @author 77160
 */
@Setter
@Getter
public class UserContactApplyQuery extends BaseParam {


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

	private String applyInfoFuzzy;


}
