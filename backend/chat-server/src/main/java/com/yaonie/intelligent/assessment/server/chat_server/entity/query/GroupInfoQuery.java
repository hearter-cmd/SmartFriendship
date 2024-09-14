package com.yaonie.intelligent.assessment.server.chat_server.entity.query;


/**
 * 群聊存档参数
 */
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

	public void setGroupNameFuzzy(String groupNameFuzzy){
		this.groupNameFuzzy = groupNameFuzzy;
	}

	public String getGroupNameFuzzy(){
		return this.groupNameFuzzy;
	}

	public void setGroupAvatar(String groupAvatar){
		this.groupAvatar = groupAvatar;
	}

	public String getGroupAvatar(){
		return this.groupAvatar;
	}

	public void setGroupAvatarFuzzy(String groupAvatarFuzzy){
		this.groupAvatarFuzzy = groupAvatarFuzzy;
	}

	public String getGroupAvatarFuzzy(){
		return this.groupAvatarFuzzy;
	}

	public void setGroupOwnerId(Long groupOwnerId){
		this.groupOwnerId = groupOwnerId;
	}

	public Long getGroupOwnerId(){
		return this.groupOwnerId;
	}

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return this.createTime;
	}

	public void setCreateTimeStart(String createTimeStart){
		this.createTimeStart = createTimeStart;
	}

	public String getCreateTimeStart(){
		return this.createTimeStart;
	}
	public void setCreateTimeEnd(String createTimeEnd){
		this.createTimeEnd = createTimeEnd;
	}

	public String getCreateTimeEnd(){
		return this.createTimeEnd;
	}

	public void setGroupNotice(String groupNotice){
		this.groupNotice = groupNotice;
	}

	public String getGroupNotice(){
		return this.groupNotice;
	}

	public void setGroupNoticeFuzzy(String groupNoticeFuzzy){
		this.groupNoticeFuzzy = groupNoticeFuzzy;
	}

	public String getGroupNoticeFuzzy(){
		return this.groupNoticeFuzzy;
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

}
