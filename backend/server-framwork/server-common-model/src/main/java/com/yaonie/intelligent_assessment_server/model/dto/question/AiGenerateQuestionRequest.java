package com.yaonie.intelligent_assessment_server.model.dto.question;


import lombok.Data;

import java.io.Serializable;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-22 23:43
 * @Author 武春利
 * @CreateTime 2024-08-22
 * @ClassName AiGenerateQuestionRequest
 * @Project backend
 * @Description : TODO
 */
@Data
public class AiGenerateQuestionRequest implements Serializable {

    /**
     * id
     */
    private Long appId;

    /**
     * 题目数
     */
    int questionNumber = 10;

    /**
     * 选项数
     */
    int optionNumber = 2;

    private static final long serialVersionUID = 1L;
}

