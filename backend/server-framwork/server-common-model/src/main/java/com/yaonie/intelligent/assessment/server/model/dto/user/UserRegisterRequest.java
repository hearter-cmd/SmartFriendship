package com.yaonie.intelligent.assessment.server.model.dto.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 而从确认的密码
     */
    private String checkPassword;

    /**
     * 验证码
     */
    private String captcha;
}
