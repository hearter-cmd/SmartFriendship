package com.yaonie.intelligent.assessment.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserQueryRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.area.GaoDeArea;
import com.yaonie.intelligent.assessment.server.common.model.model.enums.UserRoleEnum;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.LoginUserVO;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.RoleVO;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import com.yaonie.intelligent.assessment.server.common.util.NetUtils;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.server.common.util.SqlUtils;
import com.yaonie.intelligent.assessment.system.domain.dto.UserPatchPassDto;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRole;
import com.yaonie.intelligent.assessment.system.domain.vo.AdminUserInfoVO;
import com.yaonie.intelligent.assessment.system.mapper.UserMapper;
import com.yaonie.intelligent.assessment.system.service.SysRoleService;
import com.yaonie.intelligent.assessment.system.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant.REDIS_CAPTCHA_PREFIX;
import static com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant.SALT;
import static com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户服务实现
 *
 * @author yaonie
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, HttpServletRequest request, String captcha) {
        // 1. 校验
        // 验证码校验
        String captchaKey = request.getSession().getAttribute(REDIS_CAPTCHA_PREFIX).toString();
        if (StringUtils.isAnyBlank(captchaKey, captcha)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String realCaptcha = redisTemplate.opsForValue().getAndDelete(REDIS_CAPTCHA_PREFIX + captchaKey);
        if (StringUtils.isBlank(realCaptcha) || !realCaptcha.equalsIgnoreCase(captcha)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码错误");
        }
        synchronized (userAccount.intern()) {
            // 账户不能重复
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("userAccount", userAccount);
            long count = this.baseMapper.selectCount(queryWrapper);
            ThrowUtils.throwIf(count > 0, ErrorCode.PARAMS_ERROR, "账号重复");
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserName(userAccount);
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            user.setUserRoleStr(UserConstant.DEFAULT_ROLE_ID);
            user.setTags("");
            boolean saveResult = this.save(user);
            if (!saveResult) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
            }
            return user.getId();
        }
    }

    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验

        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = this.baseMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在或密码错误");
        }

        // 3. 获取用户IP以及地址
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

        // 5. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);
        // 6. 返回
        return this.getLoginUserVO(user);
    }

//    @Override
//    public LoginUserVO userLoginByMpOpen(WxOAuth2UserInfo wxOAuth2UserInfo, HttpServletRequest request) {
//        String unionId = wxOAuth2UserInfo.getUnionId();
//        String mpOpenId = wxOAuth2UserInfo.getOpenid();
//        // 单机锁
//        synchronized (unionId.intern()) {
//            // 查询用户是否已存在
//            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("unionId", unionId);
//            User user = this.getOne(queryWrapper);
//            // 被封号，禁止登录
//            if (user != null && UserRoleEnum.BAN.getValue().equals(user.getUserRole())) {
//                throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "该用户已被封，禁止登录");
//            }
//            // 用户不存在则创建
//            if (user == null) {
//                user = new User();
//                user.setUnionId(unionId);
//                user.setMpOpenId(mpOpenId);
//                user.setUserAvatar(wxOAuth2UserInfo.getHeadImgUrl());
//                user.setUserName(wxOAuth2UserInfo.getNickname());
//                boolean result = this.save(user);
//                if (!result) {
//                    throw new BusinessException(ErrorCode.SYSTEM_ERROR, "登录失败");
//                }
//            }
//            // 记录用户的登录态
//            request.getSession().setAttribute(USER_LOGIN_STATE, user);
//            return getLoginUserVO(user);
//        }
//    }

    /**
     * 获取当前登录用户（允许未登录）
     *
     * @param request Request请求对象
     * @return 登录用户信息
     */
    @Override
    public User getLoginUserPermitNull(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        return this.getById(userId);
    }

    /**
     * 是否为管理员
     *
     * @param request Request请求对象
     * @return 是否为管理员
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return isAdmin(user);
    }

    @Override
    public boolean isAdmin(User user) {
        return user != null && user.getUserRoleStr().matches(".*" + UserRoleEnum.ADMIN.getValue() + ".*");
    }

    /**
     * 用户注销
     *
     * @param request Request请求对象
     */
    @Override
    public boolean userLogout(HttpServletRequest request) {
        if (request.getSession().getAttribute(USER_LOGIN_STATE) == null) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "未登录");
        }
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return true;
    }

    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) {
            return null;
        }
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        loginUserVO.setUserRole(user.getUserRole());
        return loginUserVO;
    }

    @Override
    public UserVO getUserVO(User user) {
        if (user == null) {
            return null;
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        List<SysRole> roles = sysRoleService.listByIds(Arrays.stream(user.getUserRoleStr().split(",")).toList());
        List<RoleVO> roleVOList = roles.stream()
                .map(item -> new RoleVO(item.getRoleId(), item.getRoleKey(), item.getRoleName(), item.getEnable().equals('0')))
                .toList();
        userVO.setRoles(roleVOList);
        String tags = user.getTags();
        if (StringUtils.isNotBlank(tags)) {
            List<String> tagVOList = Arrays.stream(tags.split(",")).toList();
            userVO.setTags(tagVOList);
        }
        return userVO;
    }

    @Override
    public List<UserVO> getUserVO(List<User> userList) {
        if (CollUtil.isEmpty(userList)) {
            return new ArrayList<>();
        }
        return userList.stream().map(this::getUserVO).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest) {
        if (userQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        Long id = userQueryRequest.getId();
        String unionId = userQueryRequest.getUnionId();
        String mpOpenId = userQueryRequest.getMpOpenId();
        String userName = userQueryRequest.getUserName();
        String userProfile = userQueryRequest.getUserProfile();
        String userRole = userQueryRequest.getUserRole();
        String sortField = userQueryRequest.getSortField();
        String sortOrder = userQueryRequest.getSortOrder();
        Integer sex = userQueryRequest.getSex();
        Integer enable = userQueryRequest.getEnable();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(id != null, "id", id);
        queryWrapper.eq(StringUtils.isNotBlank(unionId), "unionId", unionId);
        queryWrapper.eq(StringUtils.isNotBlank(mpOpenId), "mpOpenId", mpOpenId);
        queryWrapper.eq(StringUtils.isNotBlank(userRole), "userRole", userRole);
        queryWrapper.eq(Objects.nonNull(sex), "sex", sex);
        queryWrapper.eq(Objects.nonNull(enable), "enable", enable);
        queryWrapper.like(StringUtils.isNotBlank(userProfile), "userProfile", userProfile);
        queryWrapper.like(StringUtils.isNotBlank(userName), "userName", userName);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取用户信息，包括用户基本信息和角色信息
     *
     * @return 用户信息的VO对象
     */
    @Override
    public AdminUserInfoVO getAdminUserInfo() {
        // 查询用户信息
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        User user = securityUser.getUser();

        // 查询用户角色信息
        List<SysRole> roles = sysRoleService.listByIds(securityUser.getRoleIds());

        // 构建 UserInfoVO
        AdminUserInfoVO userInfoVO = new AdminUserInfoVO();
        userInfoVO.setId(user.getId());
        userInfoVO.setUsername(user.getUserName());
        userInfoVO.setEnable(user.getEnable().equals('0'));
        userInfoVO.setCreateTime(user.getCreateTime().toString());
        userInfoVO.setUpdateTime(user.getUpdateTime().toString());

        AdminUserInfoVO.Profile profile = new AdminUserInfoVO.Profile();
        profile.setId(user.getId());
        profile.setNickName(user.getUserName());
        profile.setGender(user.getSex());
        profile.setAvatar(user.getUserAvatar());
        profile.setAddress(user.getAreaName());
        profile.setEmail(user.getEmail());
        profile.setUserId(user.getId());
        userInfoVO.setProfile(profile);

        List<AdminUserInfoVO.Role> roleVOs = roles.stream().map(role -> {
            AdminUserInfoVO.Role roleVO = new AdminUserInfoVO.Role();
            roleVO.setId(role.getRoleId());
            roleVO.setCode(role.getRoleKey());
            roleVO.setName(role.getRoleName());
            roleVO.setEnable("0".equals(role.getEnable()));
            return roleVO;
        }).collect(Collectors.toList());
        userInfoVO.setRoles(roleVOs);

        // 假设第一个角色为当前角色
        if (!roles.isEmpty()) {
            SysRole currentRole = roles.get(0);
            AdminUserInfoVO.Role currentRoleVO = new AdminUserInfoVO.Role();
            currentRoleVO.setId(currentRole.getRoleId());
            currentRoleVO.setCode(currentRole.getRoleKey());
            currentRoleVO.setName(currentRole.getRoleName());
            currentRoleVO.setEnable("0".equals(currentRole.getEnable()));
            userInfoVO.setCurrentRole(currentRoleVO);
        }

        return userInfoVO;
    }

    @Override
    public boolean resetPassword(UserPatchPassDto newPass, Long id) {
        String encode = passwordEncoder.encode(newPass.getPassword());
        boolean update = lambdaUpdate().eq(User::getId, id).set(User::getUserPassword, encode).update();
        return update;
    }

    @Override
    public User getUserByMpOpenId(String mpOpenId) {
        User one = lambdaQuery()
                .eq(User::getMpOpenId, mpOpenId)
                .eq(User::getIsDelete, CommonConstant.IS_NOT_DELETED)
                .eq(User::getEnable, CommonConstant.IS_ENABLE)
                .one();
        return one;
    }

    @Override
    public List<User> searchUser(String keyword) {
        ThrowUtils.throwIf(StringUtils.isBlank(keyword), ErrorCode.PARAMS_ERROR, "关键字不能为空！");
        Page<User> page = lambdaQuery()
                .eq(User::getId, keyword)
                .page(new Page<>(1, 20));
        List<User> result = lambdaQuery()
                .like(User::getUserName, keyword)
                .page(new Page<>(1, 20)).getRecords();
        if (result.isEmpty()) {
            result = new ArrayList<>();
        }
        result.addAll(page.getRecords());
        return result;
    }
}
