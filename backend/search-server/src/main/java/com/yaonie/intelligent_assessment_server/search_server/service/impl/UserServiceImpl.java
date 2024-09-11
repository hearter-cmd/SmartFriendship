package com.yaonie.intelligent_assessment_server.search_server.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent_assessment_server.common.ErrorCode;
import com.yaonie.intelligent_assessment_server.exception.BusinessException;
import com.yaonie.intelligent_assessment_server.model.entity.User;
import com.yaonie.intelligent_assessment_server.search_server.mapper.UserMapper;
import com.yaonie.intelligent_assessment_server.search_server.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import static com.yaonie.intelligent_assessment_server.constant.UserConstant.USER_LOGIN_STATE;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 20:16
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName UserServiceImpl
 * @Project backend
 * @Description : TODO
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // TODO 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }
}
