package com.yaonie.intelligent.assessment.server.common.model.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 已登录用户视图（脱敏）
 *
 * @author 77160
 */
@Data
public class LoginUserVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

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
    private List<Long> userRole;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 最后一次登录的时间
     */
    private Date lastLoginTime;

    /**
     * 最后离开时间
     */
    private Long lastLeaveTime;

    /**
     * 创建时间
     */
    private Date createTime;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}