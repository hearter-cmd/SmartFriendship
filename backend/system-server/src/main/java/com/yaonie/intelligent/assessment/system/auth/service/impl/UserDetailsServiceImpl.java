package com.yaonie.intelligent.assessment.system.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yaonie.intelligent.assessment.server.common.constant.Constants;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.util.StringUtils;
import com.yaonie.intelligent.assessment.system.domain.entity.SysMenu;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRole;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRoleMenu;
import com.yaonie.intelligent.assessment.system.mapper.UserMapper;
import com.yaonie.intelligent.assessment.system.service.SysMenuService;
import com.yaonie.intelligent.assessment.system.service.SysRoleMenuService;
import com.yaonie.intelligent.assessment.system.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  user ==> role ==> role_menu ==> menu
 * </p>
 *
 * @author 武春利
 * @since 2025-01-10
 */
@Lazy
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserMapper userMapper;
    private SysRoleService sysRoleService;
    private SysRoleMenuService sysRoleMenuService;
    private SysMenuService sysMenuService;
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名加载用户详细信息
     *
     * @param username 用户名
     * @return 用户详细信息
     * @throws UsernameNotFoundException 如果未找到用户，则抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户是否存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(User::getUserAccount, username)
                .eq(User::getEnable, CommonConstant.IS_ENABLE)
                .eq(User::getIsDelete, CommonConstant.IS_NOT_DELETED);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        user.setUserRole(Arrays.stream(user.getUserRoleStr().split(Constants.ROLE_DELIMETER)).map(Long::valueOf).toList());
        // 构造并返回用户信息
        return createSecurityUser(user);
    }

    /**
     * 根据用户对象创建安全用户对象
     *
     * @param user 用户对象
     * @return 安全用户对象
     */
    private SecurityUser createSecurityUser(User user) {
        // 校验用户是否存在
        ThrowUtils.throwIf(Objects.isNull(user), ErrorCode.PARAMS_ERROR);
        // 获取用户角色ID
        List<Long> roleIds = user.getUserRole();
        if (roleIds == null || roleIds.isEmpty()) {
            roleIds = new ArrayList<>();
            roleIds.add(Long.valueOf(UserConstant.DEFAULT_ROLE_ID));
        }
        user.setUserRole(roleIds);

        List<Long> permissionIds = new ArrayList<>();
        Set<String> permissions = new HashSet<>();
        if (roleIds.contains(UserConstant.ADMIN_ROLE)) {
            List<SysMenu> allMenuList = sysMenuService.getAllMenuList();
            permissionIds = allMenuList.stream()
                    .map(SysMenu::getId)
                    .toList();
            permissions = allMenuList.stream()
                   .map(SysMenu::getPath)
                   .filter(StringUtils::isNotBlank)
                   .collect(Collectors.toSet());
        } else {
            permissionIds = getPermissionIdsByRoleIds(roleIds);
            permissions = getPermissionsByIds(permissionIds);
        }
        // 构造并返回安全用户对象
        return new SecurityUser(user.getId(), user, user.getUserAccount(), permissions, permissionIds, roleIds);
    }

    /**
     * 根据角色键获取用户的权限集合
     *
     * @param permissionIds 权限ID集合
     * @return 用户权限集合
     */
    private Set<String> getPermissionsByIds(List<Long> permissionIds) {

        // 获取用户权限
        List<SysMenu> menuList = new ArrayList<>();
        if (!permissionIds.isEmpty()) {
            menuList = sysMenuService
                    .lambdaQuery()
                    .in(SysMenu::getName, permissionIds)
                    .eq(SysMenu::getEnable, CommonConstant.IS_ENABLE)
                    .list();
        } else {
            return new HashSet<>();
        }

        // 处理权限
        Set<String> menuPathSet = menuList
                .stream()
                .map(SysMenu::getPath)
                .collect(Collectors.toSet());

        return menuPathSet;
    }

    private List<Long> getPermissionIdsByRoleIds(List<Long> roleIds) {

        // 获取用户角色
        List<SysRole> role = sysRoleService.listByIds(roleIds);

        // 获取用户权限id
        List<SysRoleMenu> roleMenuList =
                sysRoleMenuService.getMenuIdsByRoleIds(roleIds);
        List<Long> menuIds = roleMenuList.stream()
                .map(SysRoleMenu::getMenuId)
                .toList();
        return menuIds;
    }
}
