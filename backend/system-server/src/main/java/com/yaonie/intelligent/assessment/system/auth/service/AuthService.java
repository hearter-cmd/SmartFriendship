package com.yaonie.intelligent.assessment.system.auth.service;


import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserLoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-10
 */
public interface AuthService {

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录请求对象，包含用户名和密码等信息
     * @param request          HttpServletRequest对象，包含客户端请求信息
     * @return LoginUserVO 登录成功后返回的用户信息对象，包括用户ID、用户名、角色等
     */
    Object userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request);

    boolean hasPermission(String requestURI, String method, Authentication authentication);
}
