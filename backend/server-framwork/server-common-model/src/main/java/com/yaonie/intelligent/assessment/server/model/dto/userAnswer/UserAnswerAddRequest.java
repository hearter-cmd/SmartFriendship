package com.yaonie.intelligent.assessment.server.model.dto.userAnswer;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 创建用户答案表请求
 *
 */
@Data
public class UserAnswerAddRequest implements Serializable {

    /**
     * id (用户回答唯一ID, 使用雪花算法实现幂等性
     */
    private Long id;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 用户答案（JSON 数组）
     */
    private List<String> choices;

    private static final long serialVersionUID = 1L;
}