package com.yaonie.intelligent.assessment.system.controller;


import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.system.domain.entity.SysMenu;
import com.yaonie.intelligent.assessment.system.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  菜单控制器
 * </p>
 *
 * @author 武春利
 * @since 2025-01-12
 */
@AllArgsConstructor
@RestController
@RequestMapping("/menu")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    /**
     * 获取用户的权限树
     * @return BaseResponse<List<SysMenu>>
     */
    @Operation(summary = "获取用户的权限树")
    @GetMapping("/permissions/tree")
    public BaseResponse<?> getTree() {
        List<SysMenu> menuTree = sysMenuService.getMenuTreeByUser();
        return ResultUtils.success(menuTree);
    }

    /**
     * 获取菜单树
     * @return BaseResponse<List<SysMenu>>
     */
    @Operation(summary = "获取菜单树")
    @GetMapping("/tree")
    public BaseResponse<?> getMenuTree() {
        List<SysMenu> menuTree = sysMenuService.getMenuTree();
        return ResultUtils.success(menuTree);
    }

    /**
     * 获取按钮权限列表
     */
    @Operation(summary = "获取按钮权限列表")
    @GetMapping("/button/{parentId}")
    public BaseResponse<?> getButton(@PathVariable("parentId") Long parentId) {
        List<SysMenu> menuTree = sysMenuService.getButtonByParentId(parentId);
        return ResultUtils.success(menuTree);
    }

    @Operation(summary = "更新菜单")
    @PatchMapping("/{menuId}")
    public BaseResponse<?> updateMenu(@RequestBody SysMenu sysMenu, @PathVariable("menuId") Long menuId) {
        sysMenuService.updateById(sysMenu);
        return ResultUtils.success(null);
    }

    @Operation(summary = "更新菜单")
    @PostMapping
    public BaseResponse<?> saveMenu(@RequestBody SysMenu sysMenu) {
        boolean b = sysMenuService.saveMenu(sysMenu);
        ThrowUtils.throwIf(!b, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(null);
    }

    @Operation(summary = "删除菜单")
    @DeleteMapping("/{menuId}")
    public BaseResponse<?> deleteMenu(@PathVariable("menuId") Long menuId) {
        boolean b = sysMenuService.removeById(menuId);
        ThrowUtils.throwIf(!b, ErrorCode.OPERATION_ERROR);
        return ResultUtils.success(null);
    }

}
