package com.yaonie.intelligent.assessment.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.system.domain.entity.SysMenu;
import com.yaonie.intelligent.assessment.system.mapper.SysMenuMapper;
import com.yaonie.intelligent.assessment.system.service.SysMenuService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* @author 77160
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2025-01-10 18:27:49
*/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService {

    @Lazy
    @Resource
    private SysMenuService sysMenuService;

    @Override
    public List<SysMenu> getAllMenuList() {
        List<SysMenu> list = lambdaQuery()
                .eq(SysMenu::getEnable, CommonConstant.IS_ENABLE)
                .orderByAsc(SysMenu::getOrderNum)
                .list();
        return list;
    }

    @Override
    public List<SysMenu> getMenuTreeByUser() {
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        List<Long> permissions = securityUser.getPermissionIds();
        if (permissions.isEmpty()) {
            return List.of();
        }
        List<SysMenu> list = lambdaQuery()
                .in(SysMenu::getId, permissions)
                .eq(SysMenu::getEnable, CommonConstant.IS_ENABLE)
                .orderByAsc(SysMenu::getOrderNum)
                .list();
        return buildTree(list);
    }

    @Override
    public List<SysMenu> getMenuTree() {
        List<SysMenu> allMenuList = sysMenuService.getAllMenuList();
        if (allMenuList.isEmpty()) {
            return List.of();
        }
        return buildTree(allMenuList);
    }

    @Override
    public List<SysMenu> getButtonByParentId(Long parentId) {
        List<SysMenu> list = this.lambdaQuery()
                .eq(SysMenu::getParentId, parentId)
                .eq(SysMenu::getType, "BUTTON")
                .eq(SysMenu::getEnable, CommonConstant.IS_ENABLE)
                .orderByAsc(SysMenu::getOrderNum)
                .list();
        return list;
    }

    @Override
    public boolean saveMenu(SysMenu sysMenu) {
        SysMenu one = lambdaQuery()
                .eq(SysMenu::getCode, sysMenu.getCode())
                .eq(SysMenu::getEnable, CommonConstant.IS_ENABLE)
                .one();
        if (Objects.nonNull(one)) {
            return false;
        }
        sysMenu.setCreateBy(SecurityUtils.getUsername());
        sysMenu.setCreateTime(LocalDateTime.now());
        return this.save(sysMenu);
    }

    /**
     * 构建菜单树结构
     *
     * @param list 菜单列表
     * @return 构建好的菜单树结构
     */
    private List<SysMenu> buildTree(List<SysMenu> list) {
        if (list.isEmpty()) {
            return list;
        }
        List<SysMenu> tree = new ArrayList<>();
        for (SysMenu menu : list) {
            // 修复java.util.ConcurrentModificationException: null
            if (menu.getParentId().equals(0L)) {
                tree.add(menu);
                menu.setChildren(getChildren(menu.getId(), list));
            }
        }
        return tree;
    }

    private List<SysMenu> getChildren(Long menuId, List<SysMenu> list) {
        List<SysMenu> children = new ArrayList<>();
        if (list.isEmpty() || menuId == null) {
            return children;
        }
        for (SysMenu menu : list) {
            if (menuId.equals(menu.getParentId())) {
                children.add(menu);
                menu.setChildren(getChildren(menu.getId(), list));
            }
        }
        return children;
    }


}




