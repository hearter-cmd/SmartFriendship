package com.yaonie.intelligent.assessment.system.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wf.captcha.SpecCaptcha;
import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.annotation.AuthCheck;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.DeleteRequest;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant;
import com.yaonie.intelligent.assessment.server.common.model.event.UpdateTagEvent;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserAddRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserLoginRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserPatchUpdateDTO;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserQueryRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserRegisterRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserUpdateMyRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.user.UserUpdateRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.LoginUserVO;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import com.yaonie.intelligent.assessment.server.common.util.RedisUtils;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.system.domain.dto.UserPatchPassDto;
import com.yaonie.intelligent.assessment.system.domain.vo.AdminUserInfoVO;
import com.yaonie.intelligent.assessment.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant.REDIS_CAPTCHA_PREFIX;
import static com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant.SALT;

/**
 * 用户接口
 *
 * @author yaonie
 */
@Slf4j
@Tag(name = "系统管理-用户控制器")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // region 登录相关

    /**
     * 获取验证码
     */
    @Operation(summary = "获取验证码")
    @GetMapping("/captcha")
    public BaseResponse<String> getCaptcha(HttpSession session) {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        String checkCode = specCaptcha.text();
        String checkCodeKey = UUID.randomUUID().toString().replace("-", "");
        session.setAttribute(REDIS_CAPTCHA_PREFIX, checkCodeKey);
        redisTemplate.opsForValue().set(REDIS_CAPTCHA_PREFIX + checkCodeKey, checkCode, 280, TimeUnit.SECONDS);
        return ResultUtils.success(specCaptcha.toBase64());
    }

    /**
     * 用户注册
     *
     * @param userRegisterRequest 用户注册信息
     * @return 注册结果
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody @Validated @NotNull UserRegisterRequest userRegisterRequest, HttpServletRequest request) {
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String captcha = userRegisterRequest.getCaptcha();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, request, captcha);
        return ResultUtils.success(result);
    }

    /**
     * 用户登录
     *
     * @param userLoginRequest 用户登录信息
     * @param request          请求
     * @return 登录结果
     */
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody @Validated @NotNull UserLoginRequest userLoginRequest, HttpServletRequest request) {
        // 验证码校验
        String captchaKey = request.getSession().getAttribute(REDIS_CAPTCHA_PREFIX).toString();
        String captcha = userLoginRequest.getCaptcha();
        String realCaptcha = RedisUtils.getAndDelete(REDIS_CAPTCHA_PREFIX + captchaKey);
        ThrowUtils.throwIf(!captcha.equalsIgnoreCase(realCaptcha),
                ErrorCode.PARAMS_ERROR, "验证码错误");
        log.info("captchaKey: {}, captcha: {}, realCaptcha: {}", captchaKey, captcha, realCaptcha);

        // 用户校验
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 获取超级管理员详细信息
     */
    @GetMapping("/detail")
    @Operation(summary = "获取超级管理员详细信息")
    public BaseResponse<AdminUserInfoVO> getUserInfo() {
        AdminUserInfoVO adminUserInfo = userService.getAdminUserInfo();
        return ResultUtils.success(adminUserInfo);
    }


    /**
     * 用户注销
     *
     * @param request 请求
     * @return 注销结果
     */
    @PostMapping("/logout")
    @Operation(summary = "用户注销")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    @Operation(summary = "获取当前登录用户")
    @GetMapping("/get/login")
    public BaseResponse<SecurityUser> getLoginUser() {
        SecurityUser user = SecurityUtils.getSecurityUser();
        return ResultUtils.success(user);
    }

    /**
     * 创建用户
     *
     * @param userAddRequest 用户添加请求
     * @param request        请求
     * @return 添加结果
     */
    @Operation(summary = "创建用户")
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Long> addUser(@RequestBody UserAddRequest userAddRequest, HttpServletRequest request) {
        if (userAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtils.copyProperties(userAddRequest, user);
        // 默认密码 12345678
        String defaultPassword = "12345678";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + defaultPassword).getBytes());
        user.setUserPassword(encryptPassword);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(user.getId());
    }

    /**
     * 删除用户
     *
     * @param deleteRequest 删除请求
     * @param request       请求
     * @return 删除结果
     */
    @Operation(summary = "删除用户")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @PostMapping("/delete")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@PathVariable(value = "id", required = false) Long id, @RequestBody(required = false) DeleteRequest deleteRequest, HttpServletRequest request) {
        if (Objects.nonNull(deleteRequest) && deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = false;
        if ("/user/delete".equals(request.getRequestURI())) {
            b = userService.removeById(deleteRequest.getId());
        } else if (!Objects.isNull(id)) {
            b = userService.removeById(id);
        }
        ThrowUtils.throwIf(!b, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 更新用户
     *
     * @param userUpdateRequest 用户更新请求
     * @return 更新结果
     */
    @Operation(summary = "更新用户")
    @PutMapping("/update")
    public BaseResponse<Boolean> updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        ThrowUtils.throwIf(userUpdateRequest == null || userUpdateRequest.getId() == null, ErrorCode.PARAMS_ERROR);
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        if (user.getTags() != null && !user.getTags().isBlank()) {
            eventPublisher.publishEvent(new UpdateTagEvent(Arrays.asList(user.getTags().split(","))));
        }
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    @Operation(summary = "更新用户")
    @PatchMapping("{id}")
    public BaseResponse<Boolean> updateUser(@RequestBody UserPatchUpdateDTO userPatchUpdateDTO,
                                            @PathVariable("id") Long id,
                                            HttpServletRequest request) {
        ThrowUtils.throwIf(id == null, ErrorCode.PARAMS_ERROR);
        User user = new User();
        // 设置用户ID
        user.setId(id);
        // 设置角色
        String[] roleArray = userPatchUpdateDTO.getRoleIds().toArray(new String[0]);
        String roles = StringUtils.join(roleArray, ",");
        user.setUserRoleStr(roles);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 根据 id 获取用户（仅管理员）
     *
     * @param id      用户 id
     * @param request 请求
     * @return 用户
     */
    @Operation(summary = "根据 id 获取用户（仅管理员）")
    @GetMapping("/get")
    public BaseResponse<User> getUserById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(user);
    }

    /**
     * 根据 id 获取包装类
     *
     * @param id      用户 id
     * @param request 请求
     * @return 用户
     */
    @Operation(summary = "根据 id 获取包装类")
    @GetMapping("/get/vo")
    public BaseResponse<UserVO> getUserVoById(long id, HttpServletRequest request) {
        BaseResponse<User> response = getUserById(id, request);
        User user = response.getData();
        return ResultUtils.success(userService.getUserVO(user));
    }

    @Operation(summary = "根据 id 获取包装类")
    @GetMapping("/search")
    public BaseResponse<List<UserVO>> getUserVoById(String keyword, HttpServletRequest request) {
        List<User> response = userService.searchUser(keyword);
        List<UserVO> result = response.stream().map(item -> userService.getUserVO(item)).toList();
        return ResultUtils.success(result);
    }

    /**
     * 分页获取用户列表（仅管理员）
     *
     * @param userQueryRequest 用户查询请求
     * @param request          请求
     * @return 用户列表
     */
    @Operation(summary = "分页获取用户列表（仅管理员）")
    @PostMapping("/list/page")
    public BaseResponse<Page<User>> listUserByPage(@RequestBody UserQueryRequest userQueryRequest,
                                                   HttpServletRequest request) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryRequest));
        return ResultUtils.success(userPage);
    }

    /**
     * 分页获取用户封装列表
     *
     * @param userQueryRequest 用户查询请求
     * @return 用户列表
     */
    @Operation(summary = "分页获取用户封装列表")
    @PostMapping(value = "/list/page/vo")
    public BaseResponse<Page<UserVO>> listUserVoByPage(@RequestBody UserQueryRequest userQueryRequest) {
        long current = userQueryRequest.getCurrent();
        long size = userQueryRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<User> userPage = userService.page(new Page<>(current, size),
                userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, size, userPage.getTotal());
        List<UserVO> userVO = userService.getUserVO(userPage.getRecords());
        userVOPage.setRecords(userVO);
        return ResultUtils.success(userVOPage);
    }

    // endregion

    /**
     * 更新个人信息
     *
     * @param userUpdateMyRequest 用户更新请求
     * @return 更新结果
     */
    @Operation(summary = "更新个人信息")
    @PostMapping("/update/my")
    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateMyRequest userUpdateMyRequest) {
        if (userUpdateMyRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = UserHolder.getUser();
        User user = new User();
        BeanUtils.copyProperties(userUpdateMyRequest, user);
        user.setId(loginUser.getId());
        boolean result = userService.updateById(user);
        // 更新Milvus存储的数据
        if (user.getTags() != null && !user.getTags().isBlank()) {
            eventPublisher.publishEvent(new UpdateTagEvent(Arrays.asList(user.getTags().split(","))));
        }

        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        securityUser.setUser(user);

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE, securityUser);

        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    /**
     * 修改密码
     */
    @Operation(summary = "修改密码")
    @PatchMapping(value = "/password/reset/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<Boolean> resetPassword(@RequestBody UserPatchPassDto password, @PathVariable("id") Long id) {
        boolean result = userService.resetPassword(password, id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(true);
    }

    @GetMapping("/myPermission")
    public BaseResponse<List<String>> getMyPermission() {
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        if (securityUser == null) {
            return null;
        }
        List<String> permission = securityUser.getPermissions().stream().toList();
        return ResultUtils.success(permission);
    }

}
