package com.yaonie.intelligent.assessment.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.ai.domain.model.po.ModelDictDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * AI大模型详细信息(ModelDictDetail)表数据库访问层
 *
 * @author 武春利
 * @since 2025-01-09 10:42:33
 */
@Mapper
public interface ModelDictDetailMapper extends BaseMapper<ModelDictDetail> {

}

