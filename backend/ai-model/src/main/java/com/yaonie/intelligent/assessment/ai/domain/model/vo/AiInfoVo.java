package com.yaonie.intelligent.assessment.ai.domain.model.vo;


import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-14
 */
@Builder
@Data
public class AiInfoVo {
    private String modelName;
    private String baseUrl;
    private String apiKey;
    private Integer maxTokens;
    private Float temperature;
}
