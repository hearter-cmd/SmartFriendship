package com.yaonie.intelligent.assessment.ai;

import com.yaonie.intelligent.assessment.ai.config.chat.ZhiPuChatConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-08
 */
@SpringBootApplication(scanBasePackages = {
        "com.yaonie.intelligent.assessment.ai",
        "com.yaonie.intelligent.assessment.system"
})
public class AiApplication {
    @Resource
    ZhiPuAiChatModel zhiPuAiChatModel;
    @Resource
    ZhiPuChatConfig zhiPuChatConfig;

    @PostConstruct
    public void init() {
//        zhiPuChatConfig.replaceChatModel(null);
    }

    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
    }


}
