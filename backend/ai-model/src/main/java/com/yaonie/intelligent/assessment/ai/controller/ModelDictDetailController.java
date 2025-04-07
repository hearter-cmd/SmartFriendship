package com.yaonie.intelligent.assessment.ai.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.ai.domain.model.po.ModelDictDetail;
import com.yaonie.intelligent.assessment.ai.service.ModelDictDetailService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * AI大模型详细信息(ModelDictDetail)表控制层
 *
 * @author 武春利
 * @since 2025-01-09 10:42:33
 */
@Tag(name = "AI模型字典详情信息")
@RestController
@RequestMapping("/model/detail")
public class ModelDictDetailController {
    /**
     * 服务对象
     */
    @Resource
    private ModelDictDetailService modelDictDetailService;

    /**
     * 分页查询所有数据
     *
     * @param page            分页对象
     * @param modelDictDetail 查询实体
     * @return 所有数据
     */
    @GetMapping
    @Operation(summary = "分页查询模型详情")
    public BaseResponse<Page<ModelDictDetail>> selectAll(Page<ModelDictDetail> page, ModelDictDetail modelDictDetail) {
        return ResultUtils.success(this.modelDictDetailService.page(page, new QueryWrapper<>(modelDictDetail)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param modelId 模型主键
     * @return 单条数据
     */
    @GetMapping("{modelId}")
    @Operation(summary = "模型ID查询模型详情")
    public BaseResponse<?> selectByModelId(@PathVariable String modelId) {
        List<ModelDictDetail> list = this.modelDictDetailService.getListByModelId(modelId);
        return ResultUtils.success(list);
    }

    /**
     * 新增数据
     *
     * @param modelDictDetail 实体对象
     * @return 新增结果
     */
    @PostMapping
    @Operation(summary = "插入模型详情")
    public BaseResponse<Boolean> insert(@RequestBody ModelDictDetail modelDictDetail) {
        modelDictDetail.setCreateBy(SecurityUtils.getUserId());
        modelDictDetail.setCreateTime(new Date());
        boolean save = this.modelDictDetailService.save(modelDictDetail);
        return ResultUtils.success(save);
    }

    /**
     * 修改数据
     *
     * @param modelDictDetail 实体对象
     * @return 修改结果
     */
    @PutMapping
    @Operation(summary = "修改模型详情")
    public BaseResponse<Boolean> update(@RequestBody ModelDictDetail modelDictDetail) {
        return ResultUtils.success(this.modelDictDetailService.updateById(modelDictDetail));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    @Operation(summary = "删除模型详情")
    public BaseResponse<Boolean> delete(@RequestParam("idList") List<Long> idList) {
        return ResultUtils.success(this.modelDictDetailService.removeByIds(idList));
    }

    /**
     * 根据ID删除模型详情
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除模型详情")
    public BaseResponse<?> deleteModelTypeDict(@PathVariable @NotNull @NotBlank String id) {
        boolean b = modelDictDetailService.removeById(id);
        ThrowUtils.throwIf(!b, ErrorCode.OPERATION_ERROR, "删除失败");
        return ResultUtils.success(true);
    }

    /**
     * 根据Id修改对象
     */
    @Operation(summary = "根据ID修改模型详情")
    @PatchMapping("/{id}")
    public BaseResponse<?> updateModelDictDetailById(@RequestBody ModelDictDetail bean, @PathVariable("id") String id) {
        boolean i = modelDictDetailService.updateModelDictDetailById(bean, id);
        ThrowUtils.throwIf(!i, ErrorCode.OPERATION_ERROR, "修改失败");
        return ResultUtils.success(null);
    }


}

