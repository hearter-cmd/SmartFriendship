package com.yaonie.intelligent.assessment.system.auth.handler;


import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.util.JsonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-11
 */
@Slf4j
@Component
public class FailHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BaseResponse<Object> error = null;
        if (authException instanceof InsufficientAuthenticationException) {
            log.error("认证失败", authException);
            error = ResultUtils.error(ErrorCode.NO_AUTH_ERROR, authException.getLocalizedMessage());
            response.setContentType("application/json;charset=UTF-8");
        } else {
            log.error("权限不足", authException);
            error = ResultUtils.error(ErrorCode.AUTH_ERROR, authException.getLocalizedMessage());
            response.setContentType("application/json;charset=UTF-8");
        }
        response.getWriter().write(JsonUtils.toStr(error));
    }
}
