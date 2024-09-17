package com.yaonie.intelligent.assessment.server.chat_server.user.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.DateTimePatternEnum;
import com.yaonie.intelligent.assessment.server.chat_server.utils.DateUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户
 */
public class UserBeauty implements Serializable {


	/**
	 * id
	 */
	private Long id;

	/**
	 * 账号
	 */
	private String userAccount;

	/**
	 * 密码
	 */
	private String userPassword;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 性别 0:女 1:男  2:未知
	 */
	private Integer sex;

	/**
	 * 个性签名
	 */
	private String personSignature;

	/**
	 * 微信开放平台id
	 */
	private String unionId;

	/**
	 * 公众号openId
	 */
	private String mpOpenId;

	/**
	 * 用户昵称
	 */
	private String userName;

	/**
	 * 用户头像
	 */
	private String userAvatar;

	/**
	 * 用户简介
	 */
	private String userProfile;

	/**
	 * 用户角色：user/admin/ban
	 */
	private String userRole;

	/**
	 * 添加好友的方式 0 : 直接添加; 1 : 同意后添加
	 */
	private Integer joinType;

	/**
	 * 地区名称
	 */
	private String areaName;

	/**
	 * 地区编号
	 */
	private String areaCode;

	/**
	 * 最后一次登录的时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date lastLoginTime;

	/**
	 * 最后离开时间
	 */
	private Long lastLeaveTime;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 是否删除
	 */
	private Integer isDelete;


	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setUserAccount(String userAccount){
		this.userAccount = userAccount;
	}

	public String getUserAccount(){
		return this.userAccount;
	}

	public void setUserPassword(String userPassword){
		this.userPassword = userPassword;
	}

	public String getUserPassword(){
		return this.userPassword;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setSex(Integer sex){
		this.sex = sex;
	}

	public Integer getSex(){
		return this.sex;
	}

	public void setPersonSignature(String personSignature){
		this.personSignature = personSignature;
	}

	public String getPersonSignature(){
		return this.personSignature;
	}

	public void setUnionId(String unionId){
		this.unionId = unionId;
	}

	public String getUnionId(){
		return this.unionId;
	}

	public void setMpOpenId(String mpOpenId){
		this.mpOpenId = mpOpenId;
	}

	public String getMpOpenId(){
		return this.mpOpenId;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setUserAvatar(String userAvatar){
		this.userAvatar = userAvatar;
	}

	public String getUserAvatar(){
		return this.userAvatar;
	}

	public void setUserProfile(String userProfile){
		this.userProfile = userProfile;
	}

	public String getUserProfile(){
		return this.userProfile;
	}

	public void setUserRole(String userRole){
		this.userRole = userRole;
	}

	public String getUserRole(){
		return this.userRole;
	}

	public void setJoinType(Integer joinType){
		this.joinType = joinType;
	}

	public Integer getJoinType(){
		return this.joinType;
	}

	public void setAreaName(String areaName){
		this.areaName = areaName;
	}

	public String getAreaName(){
		return this.areaName;
	}

	public void setAreaCode(String areaCode){
		this.areaCode = areaCode;
	}

	public String getAreaCode(){
		return this.areaCode;
	}

	public void setLastLoginTime(Date lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public Date getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setLastLeaveTime(Long lastLeaveTime){
		this.lastLeaveTime = lastLeaveTime;
	}

	public Long getLastLeaveTime(){
		return this.lastLeaveTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getCreateTime(){
		return this.createTime;
	}

	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}

	public Date getUpdateTime(){
		return this.updateTime;
	}

	public void setIsDelete(Integer isDelete){
		this.isDelete = isDelete;
	}

	public Integer getIsDelete(){
		return this.isDelete;
	}

	@Override
	public String toString (){
		return "id:"+(id == null ? "空" : id)+"，账号:"+(userAccount == null ? "空" : userAccount)+"，密码:"+(userPassword == null ? "空" : userPassword)+"，邮箱:"+(email == null ? "空" : email)+"，性别 0:女 1:男  2:未知:"+(sex == null ? "空" : sex)+"，个性签名:"+(personSignature == null ? "空" : personSignature)+"，微信开放平台id:"+(unionId == null ? "空" : unionId)+"，公众号openId:"+(mpOpenId == null ? "空" : mpOpenId)+"，用户昵称:"+(userName == null ? "空" : userName)+"，用户头像:"+(userAvatar == null ? "空" : userAvatar)+"，用户简介:"+(userProfile == null ? "空" : userProfile)+"，用户角色：user/admin/ban:"+(userRole == null ? "空" : userRole)+"，添加好友的方式 0 : 直接添加; 1 : 同意后添加:"+(joinType == null ? "空" : joinType)+"，地区名称:"+(areaName == null ? "空" : areaName)+"，地区编号:"+(areaCode == null ? "空" : areaCode)+"，最后一次登录的时间:"+(lastLoginTime == null ? "空" : DateUtil.format(lastLoginTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，最后离开时间:"+(lastLeaveTime == null ? "空" : lastLeaveTime)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，更新时间:"+(updateTime == null ? "空" : DateUtil.format(updateTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，是否删除:"+(isDelete == null ? "空" : isDelete);
	}
}
