package com.yaonie.intelligent_assessment_server.search_server.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent_assessment_server.model.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 17:55
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName QuestionMapper
 * @Project backend
 * @Description : TODO
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
