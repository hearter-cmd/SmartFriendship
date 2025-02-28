package com.yaonie.intelligent.assessment.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.ai.domain.model.po.ModelDictDetail;
import com.yaonie.intelligent.assessment.ai.mapper.ModelDictDetailMapper;
import com.yaonie.intelligent.assessment.ai.service.ModelDictDetailService;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * AI大模型详细信息(ModelDictDetail)表服务实现类
 *
 * @author 武春利
 * @since 2025-01-09 10:42:42
 */
@Service("modelDictDetailService")
public class ModelDictDetailServiceImpl extends ServiceImpl<ModelDictDetailMapper, ModelDictDetail> implements ModelDictDetailService {

    @Override
    public List<ModelDictDetail> getListByModelId(String modelId) {
        return this.lambdaQuery()
                .eq(ModelDictDetail::getModelId, modelId)
                .list();
    }

    /**
     * 根据Id修改
     */
    @Override
    public boolean updateModelDictDetailById(ModelDictDetail bean, String id) {
        boolean update = lambdaUpdate()
                .eq(ModelDictDetail::getId, id)
                .set(ModelDictDetail::getModelApiKey, bean.getModelApiKey())
                .set(ModelDictDetail::getUpdateBy, SecurityUtils.getUserId())
                .set(ModelDictDetail::getUpdateTime, new Date())
                .update();

        return update;
    }
}

