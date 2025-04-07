package com.yaonie.intelligent.assessment.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRoleMenu;

import java.util.List;

/**
 * @author 77160
 * @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service
 * @createDate 2025-01-10 17:35:35
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 根据角色ID获取对应的权限集合。
     *
     * @param roleId 角色ID，用于标识特定的角色。
     * @return 返回一个包含该角色所有权限的字符串集合。如果角色不存在或没有关联任何权限，则返回空集合。
     */
    List<SysRoleMenu> getMenuIdsByRoleIds(List<Long> roleId);
}
