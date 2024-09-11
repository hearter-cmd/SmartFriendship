package com.yaonie.intelligent_assessment_server.chat_server.entity.po;

import java.util.Date;
import com.yaonie.intelligent_assessment_server.chat_server.entity.enums.DateTimePatternEnum;
import com.yaonie.intelligent_assessment_server.chat_server.utils.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;


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
	private String useraccount;

	/**
	 * 密码
	 */
	private String userpassword;

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
	private String unionid;

	/**
	 * 公众号openId
	 */
	private String mpopenid;

	/**
	 * 用户昵称
	 */
	private String username;

	/**
	 * 用户头像
	 */
	private String useravatar;

	/**
	 * 用户简介
	 */
	private String userprofile;

	/**
	 * 用户角色：user/admin/ban
	 */
	private String userrole;

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
	private Date createtime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatetime;

	/**
	 * 是否删除
	 */
	private Integer isdelete;


	public void setId(Long id){
		this.id = id;
	}

	public Long getId(){
		return this.id;
	}

	public void setUseraccount(String useraccount){
		this.useraccount = useraccount;
	}

	public String getUseraccount(){
		return this.useraccount;
	}

	public void setUserpassword(String userpassword){
		this.userpassword = userpassword;
	}

	public String getUserpassword(){
		return this.userpassword;
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

	public void setUnionid(String unionid){
		this.unionid = unionid;
	}

	public String getUnionid(){
		return this.unionid;
	}

	public void setMpopenid(String mpopenid){
		this.mpopenid = mpopenid;
	}

	public String getMpopenid(){
		return this.mpopenid;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return this.username;
	}

	public void setUseravatar(String useravatar){
		this.useravatar = useravatar;
	}

	public String getUseravatar(){
		return this.useravatar;
	}

	public void setUserprofile(String userprofile){
		this.userprofile = userprofile;
	}

	public String getUserprofile(){
		return this.userprofile;
	}

	public void setUserrole(String userrole){
		this.userrole = userrole;
	}

	public String getUserrole(){
		return this.userrole;
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

	public void setCreatetime(Date createtime){
		this.createtime = createtime;
	}

	public Date getCreatetime(){
		return this.createtime;
	}

	public void setUpdatetime(Date updatetime){
		this.updatetime = updatetime;
	}

	public Date getUpdatetime(){
		return this.updatetime;
	}

	public void setIsdelete(Integer isdelete){
		this.isdelete = isdelete;
	}

	public Integer getIsdelete(){
		return this.isdelete;
	}

	@Override
	public String toString (){
		return "id:"+(id == null ? "空" : id)+"，账号:"+(useraccount == null ? "空" : useraccount)+"，密码:"+(userpassword == null ? "空" : userpassword)+"，邮箱:"+(email == null ? "空" : email)+"，性别 0:女 1:男  2:未知:"+(sex == null ? "空" : sex)+"，个性签名:"+(personSignature == null ? "空" : personSignature)+"，微信开放平台id:"+(unionid == null ? "空" : unionid)+"，公众号openId:"+(mpopenid == null ? "空" : mpopenid)+"，用户昵称:"+(username == null ? "空" : username)+"，用户头像:"+(useravatar == null ? "空" : useravatar)+"，用户简介:"+(userprofile == null ? "空" : userprofile)+"，用户角色：user/admin/ban:"+(userrole == null ? "空" : userrole)+"，添加好友的方式 0 : 直接添加; 1 : 同意后添加:"+(joinType == null ? "空" : joinType)+"，地区名称:"+(areaName == null ? "空" : areaName)+"，地区编号:"+(areaCode == null ? "空" : areaCode)+"，最后一次登录的时间:"+(lastLoginTime == null ? "空" : DateUtil.format(lastLoginTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，最后离开时间:"+(lastLeaveTime == null ? "空" : lastLeaveTime)+"，创建时间:"+(createtime == null ? "空" : DateUtil.format(createtime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，更新时间:"+(updatetime == null ? "空" : DateUtil.format(updatetime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，是否删除:"+(isdelete == null ? "空" : isdelete);
	}
}
