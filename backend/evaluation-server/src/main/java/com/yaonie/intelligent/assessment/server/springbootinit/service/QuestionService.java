package com.yaonie.intelligent.assessment.server.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.server.model.dto.question.AiGenerateQuestionRequest;
import com.yaonie.intelligent.assessment.server.model.dto.question.QuestionContextDto;
import com.yaonie.intelligent.assessment.server.model.dto.question.QuestionQueryRequest;
import com.yaonie.intelligent.assessment.server.model.entity.evaluation.Question;
import com.yaonie.intelligent.assessment.server.model.vo.QuestionVO;
import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Flowable;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * 题目服务
 *
 */
public interface QuestionService extends IService<Question> {

    /**
     * 校验数据
     *
     * @param question
     * @param add 对创建的数据进行校验
     */
    void validQuestion(Question question, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest questionQueryRequest);
    
    /**
     * 获取题目封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> questionPage, HttpServletRequest request);

    /**
     * AI根据题目类型生成题目
     * @param aiGenerateQuestionRequest
     * @return
     */
    List<QuestionContextDto> generateQuestionByAi(AiGenerateQuestionRequest aiGenerateQuestionRequest);

    /**
     * 通过AI流式生成题目
     * @param aiGenerateQuestionRequest Ai生成题目需要的参数
     * @return 流式对象
     */
    Flowable<ModelData> generateQuestionByAiStream(AiGenerateQuestionRequest aiGenerateQuestionRequest);

    void removeByAppId(Long appId);
}
