package com.yaonie.intelligent.assessment.server.chat_server.user.service;


/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-16 21:24
 * @Author 武春利
 * @CreateTime 2024-09-16
 * @ClassName LoginService
 * @Project backend
 * @Description : 
 */
public interface LoginService {
    /**
     * 刷新token有效期
     * @param token
     */
    void renewalTokenIfNecessary(String token);

    /**
     * 登录成功，获取token
     * @param uid 用户ID
     * @return 返回token
     */
    String login(Long uid);

    /**
     * 如果token有效，返回uid
     * @param token
     * @return
     */
    Long getValidUid(String token);
}
