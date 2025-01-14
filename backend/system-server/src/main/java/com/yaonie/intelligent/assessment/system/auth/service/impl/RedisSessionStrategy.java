package com.yaonie.intelligent.assessment.system.auth.service.impl;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-11
 */
public class RedisSessionStrategy implements SessionAuthenticationStrategy {
    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws SessionAuthenticationException {
        var authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {

        }
    }
}
