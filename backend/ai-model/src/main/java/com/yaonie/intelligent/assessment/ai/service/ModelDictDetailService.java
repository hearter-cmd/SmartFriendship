package com.yaonie.intelligent.assessment.ai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.ai.domain.model.po.ModelDictDetail;

import java.util.List;

/**
 * AI大模型详细信息(ModelDictDetail)表服务接口
 *
 * @author 武春利
 * @since 2025-01-09 10:42:42
 */
public interface ModelDictDetailService extends IService<ModelDictDetail> {

    List<ModelDictDetail> getListByModelId(String modelId);

    /**
     * 根据Id修改
     */
    boolean updateModelDictDetailById(ModelDictDetail bean, String id);
}

