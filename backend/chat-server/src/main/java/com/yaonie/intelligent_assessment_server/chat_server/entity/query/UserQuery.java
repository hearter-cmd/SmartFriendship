package com.yaonie.intelligent_assessment_server.chat_server.entity.query;

import lombok.Data;


/**
 * 用户参数
 */
@Data
public class UserQuery extends BaseParam {


	/**
	 * id
	 */
	private Long id;

	/**
	 * 账号
	 */
	private String useraccount;

	private String useraccountFuzzy;

	/**
	 * 密码
	 */
	private String userpassword;

	private String userpasswordFuzzy;

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
	private String personsignature;

	private String personsignatureFuzzy;

	/**
	 * 微信开放平台id
	 */
	private String unionid;

	private String unionidFuzzy;

	/**
	 * 公众号openId
	 */
	private String mpopenid;

	private String mpopenidFuzzy;

	/**
	 * 用户昵称
	 */
	private String username;

	private String usernameFuzzy;

	/**
	 * 用户头像
	 */
	private String useravatar;

	private String useravatarFuzzy;

	/**
	 * 用户简介
	 */
	private String userprofile;

	private String userprofileFuzzy;

	/**
	 * 用户角色：user/admin/ban
	 */
	private String userrole;

	private String userroleFuzzy;

	/**
	 * 添加好友的方式 0 : 直接添加; 1 : 同意后添加
	 */
	private Integer jointype;

	/**
	 * 地区名称
	 */
	private String areaname;

	private String areanameFuzzy;

	/**
	 * 地区编号
	 */
	private String areacode;

	private String areacodeFuzzy;

	/**
	 * 最后一次登录的时间
	 */
	private String lastlogintime;

	private String lastlogintimeStart;

	private String lastlogintimeEnd;

	/**
	 * 最后离开时间
	 */
	private Long lastleavetime;

	/**
	 * 创建时间
	 */
	private String createtime;

	private String createtimeStart;

	private String createtimeEnd;

	/**
	 * 更新时间
	 */
	private String updatetime;

	private String updatetimeStart;

	private String updatetimeEnd;

	/**
	 * 是否删除
	 */
	private Integer isdelete;

}
