package com.yaonie.intelligent.assessment.system.auth.controller;


import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserLoginRequest;
import com.yaonie.intelligent.assessment.system.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant.USER_LOGIN_STATE;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-09
 */
@Tag(name = "系统管理-身份认证控制器")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;


    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public BaseResponse<Object> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        Object loginUserVO = authService.userLogin(userLoginRequest, request);
        return ResultUtils.success(loginUserVO);
    }

    @Operation(summary = "用户登出")
    @PostMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return ResultUtils.success(true);
    }
}
