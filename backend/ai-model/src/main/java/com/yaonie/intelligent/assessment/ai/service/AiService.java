package com.yaonie.intelligent.assessment.ai.service;


import com.yaonie.intelligent.assessment.ai.domain.enums.AiConfigTypeEnum;
import com.yaonie.intelligent.assessment.ai.domain.model.vo.AiInfoVo;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-08
 */
public interface AiService {
    void editAiConfig(AiConfigTypeEnum aiType, String apiKey);

    AiInfoVo getCurrentAiInfo(AiConfigTypeEnum aiType);
}
