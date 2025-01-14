package com.yaonie.intelligent.assessment.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.system.domain.entity.SysMenu;

import java.util.List;

/**
* @author 77160
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
* @createDate 2025-01-10 18:27:49
*/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取所有菜单列表
     *
     * @return 包含所有菜单的列表
     */
    List<SysMenu> getAllMenuList();

    /**
     * 根据用户获取菜单树结构
     *
     * @return 包含所有菜单的树形结构列表
     */
    List<SysMenu> getMenuTreeByUser();

    /**
     * 获取所有菜单的树形结构列表
     *
     * @return 包含所有菜单的树形结构列表
     */
    List<SysMenu> getMenuTree();

    /**
     * 根据父菜单ID获取按钮列表
     *
     * @param parentId 父菜单ID
     * @return 包含按钮的列表
     */
    List<SysMenu> getButtonByParentId(Long parentId);

    /**
     * 保存菜单信息
     *
     * @param sysMenu 需要保存的菜单对象
     * @return 保存成功返回true，失败返回false
     */
    boolean saveMenu(SysMenu sysMenu);
}
