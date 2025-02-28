package com.yaonie.intelligent.assessment.ai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.ai.domain.model.po.ModelTypeDict;
import com.yaonie.intelligent.assessment.ai.mapper.ModelTypeDictMapper;
import com.yaonie.intelligent.assessment.ai.service.ModelTypeDictService;
import org.springframework.stereotype.Service;

/**
 * @author 武春利
* @description 针对表【model_type_dict(AI大模型类型)】的数据库操作Service实现
* @createDate 2025-01-09 00:42:54
*/
@Service
public class ModelTypeDictServiceImpl extends ServiceImpl<ModelTypeDictMapper, ModelTypeDict> implements ModelTypeDictService {
}




