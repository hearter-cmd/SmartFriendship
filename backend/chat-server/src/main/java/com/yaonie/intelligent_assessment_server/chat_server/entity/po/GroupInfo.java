package com.yaonie.intelligent_assessment_server.chat_server.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import com.yaonie.intelligent_assessment_server.chat_server.entity.enums.DateTimePatternEnum;
import com.yaonie.intelligent_assessment_server.chat_server.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


/**
 * 群聊存档
 */
public class GroupInfo implements Serializable {


	/**
	 * 群聊ID
	 */
	private Long groupId;

	/**
	 * 群组名
	 */
	private String groupName;

	/**
	 * 群头像链接
	 */
	private String groupAvatar;

	/**
	 * 群主ID
	 */
	private Long groupOwnerId;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 群公告
	 */
	private String groupNotice;

	/**
	 * 加入方式 (0 : 直接加入, 1 : 管理员同意后加入)
	 */
	private Integer joinType;

	/**
	 * 状态 (1 : 正常 0 : 解散)
	 */
	private Integer status;


	public void setGroupId(Long groupId){
		this.groupId = groupId;
	}

	public Long getGroupId(){
		return this.groupId;
	}

	public void setGroupName(String groupName){
		this.groupName = groupName;
	}

	public String getGroupName(){
		return this.groupName;
	}

	public void setGroupAvatar(String groupAvatar){
		this.groupAvatar = groupAvatar;
	}

	public String getGroupAvatar(){
		return this.groupAvatar;
	}

	public void setGroupOwnerId(Long groupOwnerId){
		this.groupOwnerId = groupOwnerId;
	}

	public Long getGroupOwnerId(){
		return this.groupOwnerId;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setGroupNotice(String groupNotice){
		this.groupNotice = groupNotice;
	}

	public String getGroupNotice(){
		return this.groupNotice;
	}

	public void setJoinType(Integer joinType){
		this.joinType = joinType;
	}

	public Integer getJoinType(){
		return this.joinType;
	}

	public void setStatus(Integer status){
		this.status = status;
	}

	public Integer getStatus(){
		return this.status;
	}

	@Override
	public String toString (){
		return "群聊ID:"+(groupId == null ? "空" : groupId)+"，群组名:"+(groupName == null ? "空" : groupName)+"，群头像链接:"+(groupAvatar == null ? "空" : groupAvatar)+"，群主ID:"+(groupOwnerId == null ? "空" : groupOwnerId)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，群公告:"+(groupNotice == null ? "空" : groupNotice)+"，加入方式 (0 : 直接加入, 1 : 管理员同意后加入):"+(joinType == null ? "空" : joinType)+"，状态 (1 : 正常 0 : 解散):"+(status == null ? "空" : status);
	}
}
