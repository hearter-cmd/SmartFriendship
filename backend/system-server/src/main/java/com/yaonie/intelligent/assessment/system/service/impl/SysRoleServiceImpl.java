package com.yaonie.intelligent.assessment.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.system.domain.dto.RoleAddDto;
import com.yaonie.intelligent.assessment.system.domain.dto.RolePatchDto;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRole;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRoleMenu;
import com.yaonie.intelligent.assessment.system.mapper.SysRoleMapper;
import com.yaonie.intelligent.assessment.system.service.SysRoleMenuService;
import com.yaonie.intelligent.assessment.system.service.SysRoleService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-10
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
        implements SysRoleService {
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    @Cacheable(value = "sys::role", key = "#roleKey")
    public List<SysRole> getRoleByKeys(List<String> roleKey) {
        List<SysRole> roleList = lambdaQuery()
                .eq(SysRole::getEnable, CommonConstant.IS_ENABLE)
                .eq(SysRole::getIsDelete, CommonConstant.IS_NOT_DEL)
                .in(SysRole::getRoleKey, roleKey)
                .list();
        return roleList;
    }

    @Override
    @Cacheable(value = "sys::role", key = "'enableList'")
    public List<SysRole> getRoleListByQuery(Character enable) {
        List<SysRole> list = lambdaQuery()
                .eq(SysRole::getEnable, enable)
                .eq(SysRole::getIsDelete, CommonConstant.IS_NOT_DEL)
                .list();
        return list;
    }

    @Override
    public boolean enableRole(Long id, RolePatchDto enable) {
        boolean update = lambdaUpdate()
                .eq(SysRole::getRoleId, id)
                .set(SysRole::getEnable, enable.getEnable())
                .eq(SysRole::getIsDelete, CommonConstant.IS_NOT_DEL)
                .update();
        return update;
    }

    @Override
    @Cacheable(value = "sys::role", key = "'list'")
    public List<SysRole> getRoleList() {
        List<SysRole> list = lambdaQuery()
                .eq(SysRole::getEnable, CommonConstant.IS_ENABLE)
                .eq(SysRole::getIsDelete, CommonConstant.IS_ENABLE)
                .list();
        return list;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "sys::role", key = "#roleKey"),
            @CacheEvict(value = "sys::role", key = "'list'")
    })
    public boolean removeRole(Long roleId) {
        return lambdaUpdate()
                .eq(SysRole::getRoleId, roleId)
                .eq(SysRole::getIsDelete, CommonConstant.IS_NOT_DEL)
                .remove();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @Caching(
            put = @CachePut(value = "sys::role", key = "#roleAddDto.roleKey"),
            evict = @CacheEvict(value = "sys::role", key = "'list'")
    )
    public SysRole addOrUpdate(RoleAddDto roleAddDto) {
        // 插入角色
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(roleAddDto, sysRole);
        SysRole old = lambdaQuery()
                .eq(SysRole::getRoleKey, sysRole.getRoleKey())
                .eq(SysRole::getIsDelete, CommonConstant.IS_NOT_DEL)
                .one();

        if (Objects.isNull(roleAddDto.getRoleId())) {
            ThrowUtils.throwIf(Objects.nonNull(old),
                    ErrorCode.SYSTEM_ERROR, "角色键重复");
            sysRole.setCreateBy(String.valueOf(SecurityUtils.getUserId()));
            sysRole.setCreateTime(new Date());
        } else {
            ThrowUtils.throwIf(Objects.isNull(old),
                    ErrorCode.SYSTEM_ERROR, "未找到相关角色");
            sysRole.setRoleId(String.valueOf(roleAddDto.getRoleId()));
            sysRole.setUpdateBy(String.valueOf(SecurityUtils.getUserId()));
            sysRole.setUpdateTime(new Date());
        }

        if (this.baseMapper.insertOrUpdate(sysRole)) {
            // 插入橘色权限中间表
            List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
            Optional.ofNullable(roleAddDto.getPermissionIds())
                    .orElse(new ArrayList<>())
                    .forEach(permissionId -> {
                        SysRoleMenu sysRoleMenu = new SysRoleMenu();
                        sysRoleMenu.setRoleId(sysRole.getRoleId());
                        sysRoleMenu.setMenuId(permissionId);
                        sysRoleMenus.add(sysRoleMenu);
                    });
            // 删除原有对应关系
            sysRoleMenuService.lambdaUpdate()
                    .eq(SysRoleMenu::getRoleId, sysRole.getRoleId())
                    .remove();
            // 插入角色权限
            sysRoleMenuService.saveBatch(sysRoleMenus);
            return sysRole;
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
    }


}
