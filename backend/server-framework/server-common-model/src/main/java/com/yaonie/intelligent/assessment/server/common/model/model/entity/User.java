package com.yaonie.intelligent.assessment.server.common.model.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.micrometer.common.util.StringUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户
 *
 * @author 77160
 */
@JsonTypeName("User")
@Data
@TableName(value = "sys_user")
public class User implements Serializable {

    public User() {
    }

    public User(@JsonProperty("id")Long id, @JsonProperty("userAccount")String userAccount,
                 @JsonProperty("email")String email,
                @JsonProperty("sex")Character sex, @JsonProperty("personSignature")String personSignature,
                @JsonProperty("unionId")String unionId, @JsonProperty("mpOpenId")String mpOpenId,
                @JsonProperty("userName")String userName, @JsonProperty("userAvatar")String userAvatar,
                @JsonProperty("userProfile")String userProfile, @JsonProperty("userRoleStr")String userRoleStr,
                @JsonProperty("userRole")List<Long> userRole, @JsonProperty("joinType")Integer joinType,
                @JsonProperty("ip")String ip, @JsonProperty("areaName")String areaName,
                @JsonProperty("areaCode")String areaCode, @JsonProperty("lastLoginTime")Date lastLoginTime,
                @JsonProperty("lastLeaveTime")Long lastLeaveTime, @JsonProperty("createTime")Date createTime,
                @JsonProperty("updateTime")Date updateTime, @JsonProperty("tags")String tags,
                 @JsonProperty("isDelete")Integer isDelete,
                @JsonProperty("enable")Character enable) {
        this.id = id;
        this.userAccount = userAccount;
        this.enable = enable;
        this.email = email;
        this.sex = sex;
        this.personSignature = personSignature;
        this.unionId = unionId;
        this.mpOpenId = mpOpenId;
        this.userName = userName;
        this.userAvatar = userAvatar;
        this.userProfile = userProfile;
        this.userRoleStr = userRoleStr;
        this.userRole = userRole;
        this.joinType = joinType;
        this.ip = ip;
        this.areaName = areaName;
        this.areaCode = areaCode;
        this.lastLoginTime = lastLoginTime;
        this.lastLeaveTime = lastLeaveTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.tags = tags;
        this.isDelete = isDelete;
    }

    /**
     * id
     */
    @Getter
    @Setter
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    @JsonIgnore
    @Transient
    private String userPassword;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别 0:女 1:男  2:未知
     */
    private Character sex;

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

    @TableField(value = "enable")
    private Character enable;



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

    @TableField(value = "userRole")
    private String userRoleStr;
    /**
     * 用户角色：user/admin/ban
     */
    private List<Long> userRole;

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