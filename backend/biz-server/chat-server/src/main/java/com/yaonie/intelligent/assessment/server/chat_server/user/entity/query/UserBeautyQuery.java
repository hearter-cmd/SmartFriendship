package com.yaonie.intelligent.assessment.server.chat_server.user.entity.query;


import lombok.Getter;
import lombok.Setter;

/**
 * 用户参数
 */
@Setter
@Getter
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


}
