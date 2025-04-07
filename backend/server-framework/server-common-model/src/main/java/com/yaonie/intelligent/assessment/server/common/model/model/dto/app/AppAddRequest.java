package com.yaonie.intelligent.assessment.server.common.model.model.dto.app;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 创建应用请求
 *
 * @author yaonie
 */
@Data
public class AppAddRequest implements Serializable {

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用描述
     */
    private String appDesc;

    /**
     * 应用图标
     */
    private String appIcon;

    /**
     * 应用类型（0-得分类，1-测评类）
     */
    private Integer appType;

    /**
     * 评分策略（0-自定义，1-AI）
     */
    private Integer scoringStrategy;

    @Serial
    private static final long serialVersionUID = 1L;
}