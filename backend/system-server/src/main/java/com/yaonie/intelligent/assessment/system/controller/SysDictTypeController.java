package com.yaonie.intelligent.assessment.system.controller;

import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.system.domain.entity.SysDictType;
import com.yaonie.intelligent.assessment.system.domain.page.TableDataInfo;
import com.yaonie.intelligent.assessment.system.service.SysDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据字典信息
 *
 * @author 武春利
 */
@Tag(name = "系统管理-数据字典信息", description = "数据字典信息管理")
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {
    @Resource
    private SysDictTypeService dictTypeService;

    @Operation(summary = "获取字典类型列表")
    @PreAuthorize("hasAuthority('system:dict:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysDictType dictType) {
        List<SysDictType> list = dictTypeService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    /**
     * 查询字典类型详细
     */
    @Operation(summary = "获取字典类型详细")
    @GetMapping(value = "/{dictId}")
    public BaseResponse<?> getInfo(@PathVariable Long dictId) {
        return ResultUtils.success(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @Operation(summary = "新增字典类型")
    @PostMapping
    public BaseResponse<?> add(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR,"新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(getUsername());
        return toAjax(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @Operation(summary = "修改字典类型")
    @PutMapping
    public BaseResponse<?> edit(@Validated @RequestBody SysDictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR, "修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(getUsername());
        return toAjax(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @Operation(summary = "删除字典类型")
    @DeleteMapping("/{dictIds}")
    public BaseResponse<?> remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return ResultUtils.success(null);
    }

    /**
     * 刷新字典缓存
     */
    @Operation(summary = "刷新字典缓存")
    @DeleteMapping("/refreshCache")
    public BaseResponse<?> refreshCache() {
        dictTypeService.resetDictCache();
        return ResultUtils.success(null);
    }

    /**
     * 获取字典选择框列表
     */
    @Operation(summary = "获取字典选择框列表")
    @GetMapping("/optionselect")
    public BaseResponse<?> optionselect() {
        List<SysDictType> dictTypes = dictTypeService.selectDictTypeAll();
        return ResultUtils.success(dictTypes);
    }
}
