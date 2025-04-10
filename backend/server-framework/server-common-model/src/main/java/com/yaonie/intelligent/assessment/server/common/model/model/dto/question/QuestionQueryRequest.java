package com.yaonie.intelligent.assessment.server.common.model.model.dto.question;

import com.yaonie.intelligent.assessment.server.common.model.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询题目请求
 *
 * @author yaonie
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 题目内容（json格式）
     */
    private QuestionContextDto questionContent;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * id
     */
    private Long notId;

    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 创建用户 id
     */
    private Long userId;

    @Serial
    private static final long serialVersionUID = 1L;
}