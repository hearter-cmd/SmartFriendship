package com.yaonie.intelligent.assessment.server.common.model.model.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "账号不能为空")
    @Size(min = 4, max = 32, message = "账号长度要在4-32位之间")
    private String userAccount;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 32, message = "密码长度要在8-32位之间")
    private String userPassword;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @Size(min = 4, max = 4, message = "验证码长度要在4位之间")
    private String captchaKey;

    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    private String captcha;
}
