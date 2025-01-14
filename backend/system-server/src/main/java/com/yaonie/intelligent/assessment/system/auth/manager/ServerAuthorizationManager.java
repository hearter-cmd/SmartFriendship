package com.yaonie.intelligent.assessment.system.auth.manager;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.system.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

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
public class ServerAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private AuthService authService;

    private static final String[] STATIC_RESOURCE = {
            "/css/**", "/fonts/**", "/images/**",
            "/js/**", "/v2/**", "/v3/**",
            "/swagger-ui/**", "/swagger-resources/**",
            "/api-docs/**", "/webjars/**", "/favicon.ico"
    };

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication,
                                       RequestAuthorizationContext requestAuthorizationContext) {
        return (AuthorizationDecision) authorize(authentication, requestAuthorizationContext);
    }

    @Override
    public AuthorizationResult authorize(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        // 1. admin 直接放行
        Authentication auth = authentication.get();
        SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
        if (securityUser.getRoleIds().contains(1L)) {
            return new AuthorizationDecision(true);
        }

        // 2. 校验权限
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        // 被匹配的路径
        String requestURI = request.getRequestURI();
        String requestUrl = request.getRequestURL().toString();
        log.info("请求全路径：{}", requestUrl);
        log.info("请求路径：{}", requestURI);
        String method = request.getMethod();
        log.info("请求方法：{}", method);
        // 放行OPTIONS请求, 放行白名单
        if (isStatic(requestURI) || requestURI.matches(".*/auth/.* | .*/login | .*/logout") || "OPTIONS".equals(method)) {
            return new AuthorizationDecision(true);
        }
        // 验证权限
        boolean hasPermission = authService.hasPermission(requestURI, method, authentication.get());

        return new AuthorizationDecision(hasPermission);
    }

    private boolean isStatic(String requestURI) {
        for (String s : STATIC_RESOURCE) {
            if (requestURI.matches(s)) {
                return true;
            }
        }
        return false;
    }
}
