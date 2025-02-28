package com.yaonie.intelligent.assessment.server.common.holder;


import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-11
 */
@Slf4j
public class UserHolder {
    public static final ThreadLocal<User> LOGIN_USER = new ThreadLocal<>();


    public static User getUser() {
        User user = LOGIN_USER.get();
        if (user != null) {
            return user;
        }
        User loginUser = SecurityUtils.getLoginUser();
        LOGIN_USER.set(loginUser);
        return loginUser;
    }

    public static void clear() {
        LOGIN_USER.remove();
    }

    public static void setLoginUser(User user) {
        LOGIN_USER.set(user);
    }
}
