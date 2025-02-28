package com.yaonie.intelligent.assessment.server.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.Question;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 77160
* @description 针对表【question(题目)】的数据库操作Mapper
* @createDate 2024-08-18 00:16:57
* @Entity com.yaonie.intelligent.assessment.server.springbootinit.model.entity.Question
*/
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}




