package com.yaonie.intelligent.assessment.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRoleMenu;
import com.yaonie.intelligent.assessment.system.mapper.SysRoleMenuMapper;
import com.yaonie.intelligent.assessment.system.service.SysRoleMenuService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author 77160
 * @description 针对表【sys_role_menu(角色和菜单关联表)】的数据库操作Service实现
 * @createDate 2025-01-10 17:35:35
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
        implements SysRoleMenuService {

    @Override
    @Cacheable(value = "sys::menu", key = "#roleId")
    public List<SysRoleMenu> getMenuIdsByRoleIds(List<Long> roleIds) {
        if (Objects.isNull(roleIds) || roleIds.isEmpty()) {
            return List.of();
        }
        List<SysRoleMenu> list = lambdaQuery()
                .in(SysRoleMenu::getRoleId, roleIds)
                .list();
        return list;
    }


}




