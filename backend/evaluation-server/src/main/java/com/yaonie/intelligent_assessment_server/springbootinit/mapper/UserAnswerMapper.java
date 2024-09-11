package com.yaonie.intelligent_assessment_server.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yaonie.intelligent_assessment_server.model.dto.statistic.AppAnswerCountDTO;
import com.yaonie.intelligent_assessment_server.model.dto.statistic.AppAnswerResultCountDTO;
import com.yaonie.intelligent_assessment_server.model.entity.UserAnswer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 77160
* @description 针对表【user_answer(用户答题记录)】的数据库操作Mapper
* @createDate 2024-08-18 00:16:57
* @Entity com.yaonie.intelligent_assessment_server.springbootinit.model.entity.UserAnswer
*/
@Mapper
public interface UserAnswerMapper extends BaseMapper<UserAnswer> {
    @Select("select appId, count(userId) answerCount from user_answer " +
            "group by appId order by answerCount desc limit 0, 10;")
    List<AppAnswerCountDTO> doTop10AppAnswerCount();

    @Select("select resultName, COUNT(resultName) resultCount\n" +
            "from user_answer where appId=#{appId} group by resultName order by COUNT(id) desc limit 0, 10;")
    List<AppAnswerResultCountDTO> doTop10AppAnswerResultCount(Long appId);

}




