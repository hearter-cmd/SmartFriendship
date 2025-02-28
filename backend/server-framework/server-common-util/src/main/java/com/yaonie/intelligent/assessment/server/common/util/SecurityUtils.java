package com.yaonie.intelligent.assessment.server.common.util;

import com.yaonie.intelligent.assessment.server.common.constant.HttpStatus;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * 安全服务工具类
 *
 * @author ruoyi
 */
@Slf4j
public class SecurityUtils {
    private static final ThreadLocal<SecurityUser> LOGIN_USER = new ThreadLocal<>();
    
    /**
     * 用户ID
     **/
    public static Long getUserId() {
        try {
            return getLoginUser().getId();
        } catch (Exception e) {
            throw new BusinessException(HttpStatus.UNAUTHORIZED, "获取用户ID异常");
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            return getLoginUser().getUserName();
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "获取用户账户异常");
        }
    }

    public static User getLoginUser() {
        return Objects.requireNonNull(getSecurityUser()).getUser();
    }

    /**
     * 获取用户
     **/
    public static SecurityUser getSecurityUser() {
        // 先从ThreadLocal获取
        SecurityUser securityUser = LOGIN_USER.get();
        if (securityUser != null) {
            return securityUser;
        }

        // 先判断是否已登录
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        securityUser = (SecurityUser) session.getAttribute(UserConstant.USER_LOGIN_STATE);
        return securityUser;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public static void clear() {
        LOGIN_USER.remove();
    }

    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
//    public static boolean hasPermi(String permission){
//        return hasPermi(getLoginUser().getPermissions(), permission);
//    }

    /**
     * 判断是否包含权限
     *
     * @param authorities 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
//    public static boolean hasPermi(Collection<String> authorities, String permission)
//    {
//        return authorities.stream().filter(StringUtils::hasText)
//                .anyMatch(x -> Constants.ALL_PERMISSION.equals(x) || PatternMatchUtils.simpleMatch(x, permission));
//    }

    /**
     * 验证用户是否拥有某个角色
     *
     * @param role 角色标识
     * @return 用户是否具备某角色
     */
//    public static boolean hasRole(String role)
//    {
//        List<SysRole> roleList = getLoginUser().getUser().getRoles();
//        Collection<String> roles = roleList.stream().map(SysRole::getRoleKey).collect(Collectors.toSet());
//        return hasRole(roles, role);
//    }

    /**
     * 判断是否包含角色
     *
     * @param roles 角色列表
     * @param role 角色
     * @return 用户是否具备某角色权限
     */
//    public static boolean hasRole(Collection<String> roles, String role)
//    {
//        return roles.stream().filter(StringUtils::hasText)
//                .anyMatch(x -> Constants.SUPER_ADMIN.equals(x) || PatternMatchUtils.simpleMatch(x, role));
//    }

}
