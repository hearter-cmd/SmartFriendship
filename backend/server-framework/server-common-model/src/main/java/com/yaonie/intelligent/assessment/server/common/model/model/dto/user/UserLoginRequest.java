package com.yaonie.intelligent.assessment.server.common.model.model.dto.user;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求
 * @author yaonie
 */
@Data
public class UserLoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 验证码
     */
    private String captchaKey;

    /**
     * 验证码
     */
    private String captcha;
}
