package com.yaonie.intelligent.assessment.ai.service.impl;


import com.yaonie.intelligent.assessment.ai.annotation.AiConfigType;
import com.yaonie.intelligent.assessment.ai.config.chat.ChatConfig;
import com.yaonie.intelligent.assessment.ai.domain.enums.AiConfigTypeEnum;
import com.yaonie.intelligent.assessment.ai.domain.model.vo.AiInfoVo;
import com.yaonie.intelligent.assessment.ai.service.AiService;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiChatProperties;
import org.springframework.ai.zhipuai.ZhiPuAiChatOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-08
 */
@Slf4j
@Service
public class AiServiceImpl implements AiService {
    @Resource
    private List<ChatConfig> chatConfigList;

    @Override
    public void editAiConfig(AiConfigTypeEnum aiType, String apiKey) {
        for (ChatConfig chatConfig : chatConfigList) {
            if (chatConfig.getClass().getAnnotation(AiConfigType.class).type().equals(aiType.getType())) {
                chatConfig.replaceChatModel(apiKey);
                return;
            }
        }
        throw new BusinessException(ErrorCode.PARAMS_ERROR, "AI类型错误");
    }

    @Override
    public AiInfoVo getCurrentAiInfo(AiConfigTypeEnum aiType) {
        for (ChatConfig chatConfig : chatConfigList) {
            if (chatConfig.getClass().getAnnotation(AiConfigType.class).type().equals(aiType.getType())) {
                ZhiPuAiChatProperties zhiPuProperties = chatConfig.getZhiPuProperties();
                ZhiPuAiChatOptions options = zhiPuProperties.getOptions();
                String baseUrl = zhiPuProperties.getBaseUrl();
                String apiKey = zhiPuProperties.getApiKey();
                return AiInfoVo.builder().modelName(options.getModel())
                        .baseUrl(StringUtils.isBlank(baseUrl) ? chatConfig.getBaseUrl() : baseUrl)
                        .apiKey(StringUtils.isBlank(apiKey) ? chatConfig.getApiKey() : apiKey)
                        .maxTokens(Optional
                                .ofNullable(options.getMaxTokens()).orElse(0))
                        .temperature(Optional
                                .ofNullable(options.getTemperature()).orElse(0f))
                        .build();
            }
        }
        return null;
    }
}
