package com.yaonie.intelligent.assessment.system.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserLoginRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.area.GaoDeArea;
import com.yaonie.intelligent.assessment.server.common.util.NetUtils;
import com.yaonie.intelligent.assessment.server.common.util.RedisUtils;
import com.yaonie.intelligent.assessment.server.common.util.StringUtils;
import com.yaonie.intelligent.assessment.system.auth.service.AuthService;
import com.yaonie.intelligent.assessment.system.domain.entity.SysMenu;
import com.yaonie.intelligent.assessment.system.mapper.AuthMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant.MENU_PATH;
import static com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant.REDIS_CAPTCHA_PREFIX;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-10
 */
@Service
@Slf4j
public class AuthServiceImpl extends ServiceImpl<AuthMapper, User> implements AuthService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Object userLogin(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 1. 验证码校验
        checkCaptcha(userLoginRequest, request);

        // 2. 创建用户名密码身份验证令牌
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userAccount, userPassword);

        // 2. 通过令牌进行身份验证，获取身份
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.error("error: {}", e.getMessage());
            throw e;
        }

        // 3. 获取身份信息
        ThrowUtils.throwIf(
                Objects.isNull(authenticate) || !authenticate.isAuthenticated(),
                ErrorCode.AUTH_ERROR);
        SecurityUser securityUser = (SecurityUser) authenticate.getPrincipal();
        User user = securityUser.getUser();
        log.info("登录后的用户: {}", securityUser);

        // 4. 更新用户IP以及地址
        updateLoginArea(request, user);

        // 5. 保存到SESSION
        request.getSession().setAttribute(UserConstant.USER_LOGIN_STATE, securityUser);

        // 6. 返回
//        return securityUser;
        return new Object() {
            @Getter
            private String accessToken = "access-token:admin:super-admin";
        };
    }

    @Override
    public boolean hasPermission(String requestURI, String method, Authentication authentication) {
        // 1. 从缓存获取
        Object menu = RedisUtils.hget(MENU_PATH, requestURI);
        // 2. 缓存没有查询数据库
        SysMenu sysMenu = menu == null ?
                getBaseMapper().selectMenuByPath(requestURI) : (SysMenu) menu;

        // 3. 还是没有，说明没有匹配到
        if (sysMenu == null) {
            return false;
        }

        // 4. 不需要权限就可以直接访问
        if (StringUtils.isBlank(sysMenu.getPerms())) {
            return true;
        }

        // 5. 权限校验
        var authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(sysMenu.getPerms())) {
                return true;
            }
        }
        // 6. 没有权限
        return false;
    }

    // 更新用户IP以及地址
    private void updateLoginArea(HttpServletRequest request, User user) {
        String ipAddress = NetUtils.getIpAddress(request);
        GaoDeArea ipDetail = NetUtils.getIpDetail(ipAddress);
        User newUser = new User();
        newUser.setId(user.getId());
        if (ipDetail != null && "OK".equals(ipDetail.getInfo())) {
            newUser.setAreaName(ipDetail.getProvince());
            newUser.setAreaCode(ipDetail.getAdcode());
            updateById(newUser);
        }
        user.setAreaName(newUser.getAreaName());
        user.setAreaCode(newUser.getAreaCode());
    }

    // 验证码校验
    private void checkCaptcha(UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(Objects.isNull(userLoginRequest), ErrorCode.PARAMS_ERROR);
        // 验证码校验
        String captchaKey = request.getSession().getAttribute(REDIS_CAPTCHA_PREFIX).toString();
        String captcha = userLoginRequest.getCaptcha();
        String realCaptcha = RedisUtils.getAndDelete(REDIS_CAPTCHA_PREFIX + captchaKey).replace("\"", "");
        if (log.isInfoEnabled()) {
            log.info("captchaKey: {}, captcha: {}, realCaptcha: {}",
                    captchaKey, captcha, realCaptcha);
        }
        ThrowUtils.throwIf(StringUtils.isBlank(realCaptcha) || !realCaptcha.equalsIgnoreCase(captcha),
                ErrorCode.PARAMS_ERROR, "验证码错误");
    }
}
