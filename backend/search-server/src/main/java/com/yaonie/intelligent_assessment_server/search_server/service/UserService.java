package com.yaonie.intelligent_assessment_server.search_server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent_assessment_server.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 20:16
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName UserService
 * @Project backend
 * @Description : TODO
 */
public interface UserService extends IService<User> {
    User getLoginUser(HttpServletRequest request);
}
