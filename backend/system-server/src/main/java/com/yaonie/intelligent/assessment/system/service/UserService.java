package com.yaonie.intelligent.assessment.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserQueryRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.LoginUserVO;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import com.yaonie.intelligent.assessment.system.domain.dto.UserPatchPassDto;
import com.yaonie.intelligent.assessment.system.domain.vo.AdminUserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户服务
 *
 * @author 武春利
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, HttpServletRequest request, String captcha);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request 请求
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户登录（微信开放平台）
     *
     * @param wxOAuth2UserInfo 从微信获取的用户信息
     * @param request 请求
     * @return 脱敏后的用户信息
     */
//    LoginUserVO userLoginByMpOpen(WxOAuth2UserInfo wxOAuth2UserInfo, HttpServletRequest request);

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request 请求
     * @return 脱敏后的用户信息
     */
    User getLoginUserPermitNull(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request 请求
     * @return  是否为管理员
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param user 用户
     * @return 是否为管理员
     */
    boolean isAdmin(User user);

    /**
     * 用户注销
     *
     * @param request 请求
     * @return 是否成功
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return 脱敏后的用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param user 用户
     * @return 脱敏后的用户信息
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏的用户信息
     *
     * @param userList 用户列表
     * @return 脱敏后的用户信息
     */
    List<UserVO> getUserVO(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 查询请求
     * @return 查询条件
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);


    /**
     * 获取后台用户信息
     *
     * @return 用户信息对象
     */
    AdminUserInfoVO getAdminUserInfo();

    /**
     * 重置密码
     *
     * @param id 用户的唯一标识符（ID）
     * @return 如果密码重置成功，则返回true；否则返回false
     */
    boolean resetPassword(UserPatchPassDto newPass, Long id);

    /**
     * 根据用户OPENID获取用户信息
     * @param mpOpenId 用户的唯一标识符（ID）
     * @return 用户信息对象
     */
    User getUserByMpOpenId(@Param("mpOpenId") String mpOpenId);
}
