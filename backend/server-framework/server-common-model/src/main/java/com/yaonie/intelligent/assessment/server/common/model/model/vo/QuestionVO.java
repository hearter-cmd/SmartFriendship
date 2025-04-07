package com.yaonie.intelligent.assessment.server.common.model.model.vo;

import cn.hutool.json.JSONUtil;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionContextDto;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.Question;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目视图
 *
 * @author yaonie
 */
@Data
public class QuestionVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题目内容（json格式）
     */
    private List<QuestionContextDto> questionContent;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户信息
     */
    private UserVO user;

    /**
     * 封装类转对象
     *
     * @param questionVO 封装类
     * @return 封装类
     */
    public static Question voToObj(QuestionVO questionVO) {
        if (questionVO == null) {
            return null;
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionVO, question);
        List<QuestionContextDto> questionContentDto = questionVO.getQuestionContent();
        question.setQuestionContent(JSONUtil.toJsonStr(questionContentDto));
        return question;
    }

    /**
     * 对象转封装类
     *
     * @param question 对象
     * @return 封装类
     */
    public static QuestionVO objToVo(Question question) {
        if (question == null) {
            return null;
        }
        QuestionVO questionVO = new QuestionVO();
        BeanUtils.copyProperties(question, questionVO);
        String questionContent = question.getQuestionContent();
        List<QuestionContextDto> questionContextDto = null;
        if (questionContent != null) {
            questionContextDto = JSONUtil.toList(questionContent, QuestionContextDto.class);
        }
        questionVO.setQuestionContent(questionContextDto);
        return questionVO;
    }
}
