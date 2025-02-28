package com.yaonie.intelligent.assessment.ai.config.chat;


import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiChatProperties;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-08
 */
public interface ChatConfig {
    void replaceChatModel(String apiKey);

    ZhiPuAiChatProperties getZhiPuProperties();

    String getBaseUrl();

    String getApiKey();
}
