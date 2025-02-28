package com.yaonie.intelligent.assessment.ai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.ai.domain.model.po.ModelTypeDict;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 武春利
* @description 针对表【model_type_dict(AI大模型类型)】的数据库操作Mapper
* @createDate 2025-01-09 00:42:54
* @Entity generator.domain.AiModelTypeDict
*/
@Mapper
public interface ModelTypeDictMapper extends BaseMapper<ModelTypeDict> {
}




