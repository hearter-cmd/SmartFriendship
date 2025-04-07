package com.yaonie.intelligent.assessment.server.common.model.model.dto.user;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author yaonie
 */
@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 3191241716373120793L;

    /**
     * 用户账号
     */
    @NotBlank(message = "账号不能为空")
    private String userAccount;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String userPassword;

    /**
     * 而从确认的密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String checkPassword;

    /**
     * 验证码
     */
    private String captcha;

    @AssertTrue(message = "密码和确认密码不一致")
    public boolean isPasswordEqualsCheckPassword() {
        return userPassword != null && userPassword.equals(checkPassword);
    }
}
