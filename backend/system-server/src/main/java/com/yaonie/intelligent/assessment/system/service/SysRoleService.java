package com.yaonie.intelligent.assessment.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.system.domain.dto.RoleAddDto;
import com.yaonie.intelligent.assessment.system.domain.dto.RolePatchDto;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRole;

import java.util.List;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author 武春利
 * @since 2025-01-10
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 根据角色键获取角色信息。
     *
     * @param roleKey 角色键，用于唯一标识一个角色。
     * @return 返回与给定角色键对应的角色信息对象。如果未找到对应的角色，则返回null。
     */
    public List<SysRole> getRoleByKeys(List<String> roleKey);

    /**
     * 获取系统角色列表
     *
     * @return 系统角色列表
     */
    public List<SysRole> getRoleList();


    /**
     * 删除指定角色键对应的角色。
     *
     * @param roleId 角色键，用于唯一标识一个角色。
     */
    public boolean removeRole(Long roleId);

    /**
     * 添加或更新系统角色信息。
     *
     * @param roleAddDto 要添加或更新的系统角色对象，包含角色的详细信息。
     */
    public SysRole addOrUpdate(RoleAddDto roleAddDto);

    public List<SysRole> getRoleListByQuery(Character enable);

    boolean enableRole(Long id, RolePatchDto enable);
}
