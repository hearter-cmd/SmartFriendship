package com.yaonie.intelligent.assessment.system.auth.filter;

import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-11
 */
@Component
@WebFilter(urlPatterns = "/**")
public class SecurityFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 1. 获取用户信息
            SecurityUser securityUser = SecurityUtils.getSecurityUser();

            // 2. 校验用户是否为空
            if (Objects.isNull(securityUser)) {
                filterChain.doFilter(request, response);
                return;
            }

            // 放入Security 用户对象，密码，权限
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    securityUser, securityUser.getUser().getUserPassword(), securityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            // 通过权限校验，放行
            filterChain.doFilter(request, response);
        } finally {
            // 释放资源
            SecurityUtils.clear();
        }

    }
}
