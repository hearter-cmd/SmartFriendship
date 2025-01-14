package com.yaonie.intelligent.assessment.system.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.server.common.model.common.BasePage;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.RoleVO;
import com.yaonie.intelligent.assessment.system.domain.dto.RoleAddDto;
import com.yaonie.intelligent.assessment.system.domain.dto.RolePatchDto;
import com.yaonie.intelligent.assessment.system.domain.entity.SysRole;
import com.yaonie.intelligent.assessment.system.service.SysRoleService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-12
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public BaseResponse<?> page(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "enable", required = false) Boolean enable,
            @RequestParam(name = "current") Integer pageNo,
            @RequestParam(name = "pageSize") Integer pageSize) {
        Page<SysRole> page = sysRoleService.lambdaQuery()
                .like(StringUtils.isNotBlank(name), SysRole::getRoleName, name)
                .eq(enable != null, SysRole::getEnable, Boolean.TRUE.equals(enable) ? 1 : 0)
                .page(new Page<>(pageNo, pageSize));
        return ResultUtils.success(BasePage.build(page.getRecords(), page.getTotal()));
    }

    @Operation(summary = "获取角色列表")
    @GetMapping
    public BaseResponse<?> getRoleList(Character enable) {
        List<SysRole> enableRoleList = sysRoleService.getRoleListByQuery(enable);
        List<RoleVO> list = enableRoleList.stream()
                .map(item -> new RoleVO(item.getRoleId(),  item.getRoleKey(), item.getRoleName(),item.getEnable().equals('0')))
                .toList();
        return ResultUtils.success(list);
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.POST})
    public BaseResponse<?> addOrUpdateRole(@RequestBody RoleAddDto roleAddDto) {
        ThrowUtils.throwIf(roleAddDto == null, ErrorCode.PARAMS_ERROR);
        SysRole sysRole = sysRoleService.addOrUpdate(roleAddDto);
        return ResultUtils.success(sysRole);
    }

    /**
     * 修改角色是否启用
     * @param id 角色id
     * @return BaseResponse
     */
    @PatchMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse<?> enableUser(@RequestBody RolePatchDto rolePatchDto, @PathVariable(name = "id") Long id) {
        boolean b = sysRoleService.enableRole(id, rolePatchDto);
        ThrowUtils.throwIf(!b, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(Boolean.valueOf(b));
    }


    @DeleteMapping("{id}")
    public BaseResponse<?> deleteRole(@PathVariable(name = "id") Long id) {
        boolean b = sysRoleService.removeRole(id);
        ThrowUtils.throwIf(b, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(b);
    }

}
