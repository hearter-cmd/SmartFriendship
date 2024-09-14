package com.yaonie.intelligent.assessment.server.model.dto;

import lombok.Data;

/**
 * @author 77160
 */
@Data
public class QuestionAnswerDTO {

    /**
     * 题目
     */
    private String title;

    /**
     * 用户答案
     */
    private String userAnswer;
}
