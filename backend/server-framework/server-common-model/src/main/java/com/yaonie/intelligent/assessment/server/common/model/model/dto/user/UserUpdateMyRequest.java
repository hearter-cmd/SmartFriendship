package com.yaonie.intelligent.assessment.server.common.model.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户更新个人信息请求
 *
 * @author yaonie
 */
@Data
public class UserUpdateMyRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

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
    private String personSignature;

    /**
     * 简介
     */
    private String userProfile;

    /**
     * 用户标签列表 JSON
     */
    private String tags;

    @Serial
    private static final long serialVersionUID = 1L;
}