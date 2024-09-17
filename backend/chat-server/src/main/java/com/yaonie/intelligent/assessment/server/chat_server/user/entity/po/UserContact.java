package com.yaonie.intelligent.assessment.server.chat_server.user.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.DateTimePatternEnum;
import com.yaonie.intelligent.assessment.server.chat_server.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 联系人关系表
 */
public class UserContact implements Serializable {


	/**
	 * 用户ID
	 */
	private Long userId;

	/**
	 * 其他用户ID 或 群组ID
	 */
	private Long contactId;

	/**
	 * 联系人类型 (0 : 好友 1 : 群组)
	 */
	private Integer contactType;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 状态 0:非好友 1:好友 2:已删除好友 3:被好友删除 4:已拉黑好友 5:被好友拉黑
	 */
	private Integer status;


	public void setUserId(Long userId){
		this.userId = userId;
	}

	public Long getUserId(){
		return this.userId;
	}

	public void setContactId(Long contactId){
		this.contactId = contactId;
	}

	public Long getContactId(){
		return this.contactId;
	}

	public void setContactType(Integer contactType){
		this.contactType = contactType;
	}

	public Integer getContactType(){
		return this.contactType;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	@Override
	public String toString (){
		return "用户ID:"+(userId == null ? "空" : userId)+"，其他用户ID 或 群组ID:"+(contactId == null ? "空" : contactId)+"，联系人类型 (0 : 好友 1 : 群组):"+(contactType == null ? "空" : contactType)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，状态 0:非好友 1:好友 2:已删除好友 3:被好友删除 4:已拉黑好友 5:被好友拉黑:"+(status == null ? "空" : status);
	}
}
