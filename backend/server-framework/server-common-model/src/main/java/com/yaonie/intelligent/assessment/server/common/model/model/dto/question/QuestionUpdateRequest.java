package com.yaonie.intelligent.assessment.server.common.model.model.dto.question;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 更新题目请求
 * @author yaonie
 */
@Data
public class QuestionUpdateRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题目内容（json格式）
     */
    private List<QuestionContextDto> questionContent;

    @Serial
    private static final long serialVersionUID = 1L;
}