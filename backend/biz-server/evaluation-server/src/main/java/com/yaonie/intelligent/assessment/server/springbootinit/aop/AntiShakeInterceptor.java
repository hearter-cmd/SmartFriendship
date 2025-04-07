package com.yaonie.intelligent.assessment.server.springbootinit.aop;


import cn.hutool.crypto.SecureUtil;
import com.yaonie.intelligent.assessment.server.common.model.annotation.AntiShake;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.util.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 防抖拦截器
 * </p>
 *
 * @author 武春利
 * @since 2025-01-01
 */
@Component
public class AntiShakeInterceptor implements HandlerInterceptor {

    @Value("${server.frequency}:5")
    private static Integer FREQUENCY;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        // 非方法不处理
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        // 获取方法
        Method method = handlerMethod.getMethod();
        AntiShake annotation = method.getAnnotation(AntiShake.class);
        if (Objects.isNull(annotation)) {
            return true;
        }

        // 获取SessionId
        String requestURI = request.getRequestURI();
        String requestedSessionId = request.getRequestedSessionId();
        String md5Id = SecureUtil.md5(String.join("_", requestURI, requestedSessionId));

        // 判断请求是否过于频繁
        if (RedisUtils.hasKey(md5Id)) {
            throw new BusinessException(500, "请求过于频繁");
        }
        RedisUtils.set(md5Id, 2, FREQUENCY, TimeUnit.SECONDS);
        return true;
    }
}
