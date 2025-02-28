package com.yaonie.intelligent.assessment.server.search_server.aop;

import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.enums.UserRoleEnum;
import com.yaonie.intelligent.assessment.server.search_server.annotation.AuthCheck;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.util.List;


/**
 * 权限校验 AOP
 *
 * @author 77160
 */
//@Aspect
//@Component
public class AuthInterceptor {

    /**
     * 执行拦截
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        Long mustRole = authCheck.mustRole();
        // 当前登录用户
        User loginUser = UserHolder.getUser();
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        // 不需要权限，放行
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }
        // 必须有该权限才通过
        List<UserRoleEnum> roleEnumList = loginUser.getUserRole()
                .stream()
                .map(UserRoleEnum::getEnumByValue)
                .toList();
        if (roleEnumList.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 如果被封号，直接拒绝
        if (roleEnumList.contains(UserRoleEnum.BAN)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // 必须有管理员权限
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum)) {
            // 用户没有管理员权限，拒绝
            if (!roleEnumList.contains(UserRoleEnum.ADMIN)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}

