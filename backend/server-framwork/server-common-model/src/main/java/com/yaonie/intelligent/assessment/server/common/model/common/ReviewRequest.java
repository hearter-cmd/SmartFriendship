package com.yaonie.intelligent.assessment.server.common.model.common;


import lombok.Data;

import java.io.Serializable;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-18 20:57
 * @Author 武春利
 * @CreateTime 2024-08-18
 * @ClassName ReviewRequest
 * @Project backend
 * @Description : 审核表单
 */
@Data
public class ReviewRequest implements Serializable {

    /**
     * 应用ID
     */
    private Long id;

    /**
     * 审核状态 0 - 未审核, 1 - 审核通过, 2 - 审核不通过
     */
    private Integer reviewStatus;

    /**
     * 评审信息
     */
    private String reviewMessage;

    private static final long serialVersionUID = 1L;
}
