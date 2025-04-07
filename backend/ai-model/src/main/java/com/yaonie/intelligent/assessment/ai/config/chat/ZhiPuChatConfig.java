package com.yaonie.intelligent.assessment.ai.config.chat;


import cn.hutool.extra.spring.SpringUtil;
import com.yaonie.intelligent.assessment.ai.annotation.AiConfigType;
import com.yaonie.intelligent.assessment.server.common.util.StringUtils;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.ai.autoconfigure.zhipuai.ZhiPuAiChatProperties;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.api.ZhiPuAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-08
 */
@Setter
@Getter
@AiConfigType(type = "ZhiPuChat")
@Component
public class ZhiPuChatConfig implements ChatConfig {

    @Resource
    private ZhiPuAiChatProperties zhiPuAiChatProperties;

    @Value("${spring.ai.zhipuai.api-key}")
    private String apiKey;

    @Value("${spring.ai.zhipuai.base-url: https://open.bigmodel.cn/api/paas}")
    private String baseUrl;

    @Override
    public void replaceChatModel(String apiKey) {
        ZhiPuAiChatModel zhiPuAiChatModel;
        if (StringUtils.isNotBlank(apiKey)) {
            zhiPuAiChatProperties.setApiKey(apiKey);
        }
        ZhiPuAiApi zhiPuAiApi = new ZhiPuAiApi(zhiPuAiChatProperties.getBaseUrl(), zhiPuAiChatProperties.getApiKey());
        zhiPuAiChatModel = new ZhiPuAiChatModel(zhiPuAiApi, zhiPuAiChatProperties.getOptions());
        this.apiKey = apiKey;
        this.baseUrl = zhiPuAiChatProperties.getBaseUrl();
        SpringUtil.unregisterBean("zhiPuAiChatModel");
        SpringUtil.registerBean("zhiPuAiChatModel", zhiPuAiChatModel);
    }

    @Override
    public ZhiPuAiChatProperties getZhiPuProperties() {
        return zhiPuAiChatProperties;
    }
}
