package com.yaonie.intelligent.assessment.system.auth.filter;

import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.util.NetUtils;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            initPV();
            initUV(request);

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
            SecurityContextHolder.getContext()
                    .setAuthentication(usernamePasswordAuthenticationToken);

            // 通过权限校验，放行
            filterChain.doFilter(request, response);
        } finally {
            // 释放资源
            if (SecurityUtils.getSecurityUser() != null) {
                SecurityUtils.clear();
            }
            if (UserHolder.LOGIN_USER.get() != null) {
                UserHolder.clear();
            }
        }
    }

    //redis的pv和uv前缀
    final static String PV_PREFIX = "pv_";
    final static String UV_PREFIX = "uv_";

    private void initPV() {
        String today = getDateYYYYMMDD();
        String pvKey = PV_PREFIX + today;
        // pv + 1, incr命令:将 key 中储存的数字值增一。如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
        redisTemplate.opsForValue().increment(pvKey);
    }

    private void initUV(HttpServletRequest request) {
        String today = getDateYYYYMMDD();
        String uvKey = UV_PREFIX + today;
        // 获取用户IP
        String clientIP = NetUtils.getIpAddress(request);
        // 将ip放到redis的HyperLogLog中
        redisTemplate.opsForHyperLogLog().add(uvKey, clientIP); //PFADD mypf ip1 ip2
    }

    private String getDateYYYYMMDD() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(date);
        return today;
    }
}
