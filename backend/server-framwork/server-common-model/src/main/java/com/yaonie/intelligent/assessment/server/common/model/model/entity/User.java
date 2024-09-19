package com.yaonie.intelligent.assessment.server.common.model.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.micrometer.common.util.StringUtils;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author 77160
 */
@TableName(value = "user")
@Data
public class User implements Serializable {

    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    @Transient
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
    @Transient
    private String personSignature;

    /**
     * 微信开放平台id
     */
    @Transient
    private String unionId;

    /**
     * 公众号openId
     */
    @Transient
    private String mpOpenId;

    /**
     * 用户昵称
     */
    @Transient
    private String userName;

    /**
     * 用户头像
     */
    @Transient
    private String userAvatar;

    /**
     * 用户简介
     */
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    @Transient
    private String userRole;

    /**
     * 添加好友的方式 0 : 直接添加; 1 : 同意后添加
     */
    @Transient
    private Integer joinType;

    @TableField(value = "ip")
    private String ip;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 地区编号
     */
    @Transient
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
     * 用户标签列表 JSON
     */
    private String tags;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public void refreshIp(String ip) {
        if (StringUtils.isBlank(this.ip)) {
            // 第一次注册保存ip
            this.ip = ip;
        }
        this.setLastLoginTime(new Date());
    }
}