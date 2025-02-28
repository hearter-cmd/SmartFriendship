package com.yaonie.intelligent.assessment.ai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.ai.domain.model.po.ModelTypeDict;
import com.yaonie.intelligent.assessment.ai.service.ModelTypeDictService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-09
 */
@Tag(name = "AI模型类型字典")
@RestController
@RequestMapping("/model")
public class ModelTypeDictController {
    @Resource
    private ModelTypeDictService modelTypeDictService;

    /**
     * 获取所有模型类型字典
     *
     * @return 模型类型字典列表
     */
    @GetMapping
    @Operation(summary = "获取所有模型类型字典")
    public BaseResponse<?> getAllModelTypeDicts() {
        Page<ModelTypeDict> page = modelTypeDictService
                .page(new Page<>(1, 10));
        return ResultUtils.success(page);
    }

    /**
     * 根据ID获取模型类型字典
     *
     * @param id 模型类型字典ID
     * @return 模型类型字典
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取模型类型字典")
    public ModelTypeDict getModelTypeDictById(@PathVariable String id) {
        return modelTypeDictService.getById(id);
    }

    /**
     * 添加模型类型字典
     *
     * @param modelTypeDict 模型类型字典对象
     * @return 添加后的模型类型字典
     */
    @PostMapping
    @Operation(summary = "添加模型类型字典")
    public BaseResponse<Boolean> addModelTypeDict(@RequestBody ModelTypeDict modelTypeDict) {
        modelTypeDict.setCreateBy(SecurityUtils.getUserId());
        modelTypeDict.setCreateTime(new Date());
        boolean save = modelTypeDictService.save(modelTypeDict);
        ThrowUtils.throwIf(!save, ErrorCode.OPERATION_ERROR, "添加失败");
        return ResultUtils.success(true);
    }

    /**
     * 更新模型类型字典
     *
     * @param modelTypeDict 模型类型字典对象
     * @return 更新后的模型类型字典
     */
    @PatchMapping("/{id}")
    @Operation(summary = "更新模型类型字典")
    public BaseResponse<?> updateModelTypeDict(@RequestBody ModelTypeDict modelTypeDict) {
        modelTypeDictService.updateById(modelTypeDict);
        return ResultUtils.success(modelTypeDict);
    }

    /**
     * 根据ID删除模型类型字典
     *
     * @param id 模型类型字典ID
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除模型类型字典")
    public BaseResponse<?> deleteModelTypeDict(@PathVariable @NotNull @NotBlank String id) {
        boolean b = modelTypeDictService.removeById(id);
        ThrowUtils.throwIf(!b, ErrorCode.OPERATION_ERROR, "删除失败");
        return ResultUtils.success(true);
    }

    /**
     * 分页查询模型类型字典
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页结果
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询模型类型字典")
    public BaseResponse<Page<ModelTypeDict>> getModelTypeDictPage(@RequestParam int page, @RequestParam int size) {
        Page<ModelTypeDict> result = modelTypeDictService.page(new Page<>(page, size));
        return ResultUtils.success(result);
    }

    /**
     * 根据条件查询模型类型字典
     *
     * @param modelTypeDict 查询条件
     * @return 符合条件的模型类型字典列表
     */
    @PostMapping("/search")
    @Operation(summary = "根据条件查询模型类型字典")
    public BaseResponse<List<ModelTypeDict>> searchModelTypeDictList(@RequestBody ModelTypeDict modelTypeDict) {
        QueryWrapper<ModelTypeDict> queryWrapper = new QueryWrapper<>(modelTypeDict);
        List<ModelTypeDict> list = modelTypeDictService.list(queryWrapper);
        return ResultUtils.success(list);
    }

}
