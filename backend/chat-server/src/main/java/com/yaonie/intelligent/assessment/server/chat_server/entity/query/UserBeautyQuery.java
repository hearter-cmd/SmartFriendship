package com.yaonie.intelligent.assessment.server.chat_server.entity.query;


/**
 * 用户参数
 */
public class UserBeautyQuery extends BaseParam {


	/**
	 * id
	 */
	private Long id;

	/**
	 * 账号
	 */
	private String userAccount;

	private String userAccountFuzzy;

	/**
	 * 密码
	 */
	private String userPassword;

	private String userPasswordFuzzy;

	/**
	 * 邮箱
	 */
	private String email;

	private String emailFuzzy;

	/**
	 * 性别 0:女 1:男  2:未知
	 */
	private Integer sex;

	/**
	 * 个性签名
	 */
	private String personSignature;

	private String personSignatureFuzzy;

	/**
	 * 微信开放平台id
	 */
	private String unionId;

	private String unionIdFuzzy;

	/**
	 * 公众号openId
	 */
	private String mpOpenId;

	private String mpOpenIdFuzzy;

	/**
	 * 用户昵称
	 */
	private String userName;

	private String userNameFuzzy;

	/**
	 * 用户头像
	 */
	private String userAvatar;

	private String userAvatarFuzzy;

	/**
	 * 用户简介
	 */
	private String userProfile;

	private String userProfileFuzzy;

	/**
	 * 用户角色：user/admin/ban
	 */
	private String userRole;

	private String userRoleFuzzy;

	/**
	 * 添加好友的方式 0 : 直接添加; 1 : 同意后添加
	 */
	private Integer joinType;

	/**
	 * 地区名称
	 */
	private String areaName;

	private String areaNameFuzzy;

	/**
	 * 地区编号
	 */
	private String areaCode;

	private String areaCodeFuzzy;

	/**
	 * 最后一次登录的时间
	 */
	private String lastLoginTime;

	private String lastLoginTimeStart;

	private String lastLoginTimeEnd;

	/**
	 * 最后离开时间
	 */
	private Long lastLeaveTime;

	/**
	 * 创建时间
	 */
	private String createTime;

	private String createTimeStart;

	private String createTimeEnd;

	/**
	 * 更新时间
	 */
	private String updateTime;

	private String updateTimeStart;

	private String updateTimeEnd;

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

	public void setUserAccountFuzzy(String userAccountFuzzy){
		this.userAccountFuzzy = userAccountFuzzy;
	}

	public String getUserAccountFuzzy(){
		return this.userAccountFuzzy;
	}

	public void setUserPassword(String userPassword){
		this.userPassword = userPassword;
	}

	public String getUserPassword(){
		return this.userPassword;
	}

	public void setUserPasswordFuzzy(String userPasswordFuzzy){
		this.userPasswordFuzzy = userPasswordFuzzy;
	}

	public String getUserPasswordFuzzy(){
		return this.userPasswordFuzzy;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setEmailFuzzy(String emailFuzzy){
		this.emailFuzzy = emailFuzzy;
	}

	public String getEmailFuzzy(){
		return this.emailFuzzy;
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

	public void setPersonSignatureFuzzy(String personSignatureFuzzy){
		this.personSignatureFuzzy = personSignatureFuzzy;
	}

	public String getPersonSignatureFuzzy(){
		return this.personSignatureFuzzy;
	}

	public void setUnionId(String unionId){
		this.unionId = unionId;
	}

	public String getUnionId(){
		return this.unionId;
	}

	public void setUnionIdFuzzy(String unionIdFuzzy){
		this.unionIdFuzzy = unionIdFuzzy;
	}

	public String getUnionIdFuzzy(){
		return this.unionIdFuzzy;
	}

	public void setMpOpenId(String mpOpenId){
		this.mpOpenId = mpOpenId;
	}

	public String getMpOpenId(){
		return this.mpOpenId;
	}

	public void setMpOpenIdFuzzy(String mpOpenIdFuzzy){
		this.mpOpenIdFuzzy = mpOpenIdFuzzy;
	}

	public String getMpOpenIdFuzzy(){
		return this.mpOpenIdFuzzy;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return this.userName;
	}

	public void setUserNameFuzzy(String userNameFuzzy){
		this.userNameFuzzy = userNameFuzzy;
	}

	public String getUserNameFuzzy(){
		return this.userNameFuzzy;
	}

	public void setUserAvatar(String userAvatar){
		this.userAvatar = userAvatar;
	}

	public String getUserAvatar(){
		return this.userAvatar;
	}

	public void setUserAvatarFuzzy(String userAvatarFuzzy){
		this.userAvatarFuzzy = userAvatarFuzzy;
	}

	public String getUserAvatarFuzzy(){
		return this.userAvatarFuzzy;
	}

	public void setUserProfile(String userProfile){
		this.userProfile = userProfile;
	}

	public String getUserProfile(){
		return this.userProfile;
	}

	public void setUserProfileFuzzy(String userProfileFuzzy){
		this.userProfileFuzzy = userProfileFuzzy;
	}

	public String getUserProfileFuzzy(){
		return this.userProfileFuzzy;
	}

	public void setUserRole(String userRole){
		this.userRole = userRole;
	}

	public String getUserRole(){
		return this.userRole;
	}

	public void setUserRoleFuzzy(String userRoleFuzzy){
		this.userRoleFuzzy = userRoleFuzzy;
	}

	public String getUserRoleFuzzy(){
		return this.userRoleFuzzy;
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

	public void setAreaNameFuzzy(String areaNameFuzzy){
		this.areaNameFuzzy = areaNameFuzzy;
	}

	public String getAreaNameFuzzy(){
		return this.areaNameFuzzy;
	}

	public void setAreaCode(String areaCode){
		this.areaCode = areaCode;
	}

	public String getAreaCode(){
		return this.areaCode;
	}

	public void setAreaCodeFuzzy(String areaCodeFuzzy){
		this.areaCodeFuzzy = areaCodeFuzzy;
	}

	public String getAreaCodeFuzzy(){
		return this.areaCodeFuzzy;
	}

	public void setLastLoginTime(String lastLoginTime){
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginTime(){
		return this.lastLoginTime;
	}

	public void setLastLoginTimeStart(String lastLoginTimeStart){
		this.lastLoginTimeStart = lastLoginTimeStart;
	}

	public String getLastLoginTimeStart(){
		return this.lastLoginTimeStart;
	}
	public void setLastLoginTimeEnd(String lastLoginTimeEnd){
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}

	public String getLastLoginTimeEnd(){
		return this.lastLoginTimeEnd;
	}

	public void setLastLeaveTime(Long lastLeaveTime){
		this.lastLeaveTime = lastLeaveTime;
	}

	public Long getLastLeaveTime(){
		return this.lastLeaveTime;
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

	public void setUpdateTime(String updateTime){
		this.updateTime = updateTime;
	}

	public String getUpdateTime(){
		return this.updateTime;
	}

	public void setUpdateTimeStart(String updateTimeStart){
		this.updateTimeStart = updateTimeStart;
	}

	public String getUpdateTimeStart(){
		return this.updateTimeStart;
	}
	public void setUpdateTimeEnd(String updateTimeEnd){
		this.updateTimeEnd = updateTimeEnd;
	}

	public String getUpdateTimeEnd(){
		return this.updateTimeEnd;
	}

	public void setIsDelete(Integer isDelete){
		this.isDelete = isDelete;
	}

	public Integer getIsDelete(){
		return this.isDelete;
	}

}
