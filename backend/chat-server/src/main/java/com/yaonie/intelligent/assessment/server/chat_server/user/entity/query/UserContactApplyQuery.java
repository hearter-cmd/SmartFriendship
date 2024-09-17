package com.yaonie.intelligent.assessment.server.chat_server.user.entity.query;



/**
 * 用户人申请表参数
 */
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


	public void setApplyId(Integer applyId){
		this.applyId = applyId;
	}

	public Integer getApplyId(){
		return this.applyId;
	}

	public void setApplyUserId(Long applyUserId){
		this.applyUserId = applyUserId;
	}

	public Long getApplyUserId(){
		return this.applyUserId;
	}

	public void setReceiveUserId(Long receiveUserId){
		this.receiveUserId = receiveUserId;
	}

	public Long getReceiveUserId(){
		return this.receiveUserId;
	}

	public void setConcatType(Integer concatType){
		this.concatType = concatType;
	}

	public Integer getConcatType(){
		return this.concatType;
	}

	public void setConcatId(Long concatId){
		this.concatId = concatId;
	}

	public Long getConcatId(){
		return this.concatId;
	}

	public void setLastApplyTime(Long lastApplyTime){
		this.lastApplyTime = lastApplyTime;
	}

	public Long getLastApplyTime(){
		return this.lastApplyTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	public void setApplyInfo(String applyInfo){
		this.applyInfo = applyInfo;
	}

	public String getApplyInfo(){
		return this.applyInfo;
	}

	public void setApplyInfoFuzzy(String applyInfoFuzzy){
		this.applyInfoFuzzy = applyInfoFuzzy;
	}

	public String getApplyInfoFuzzy(){
		return this.applyInfoFuzzy;
	}

}
