package com.yaonie.intelligent.assessment.ai;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
//        zhiPuChatConfig.replaceChatModel(null);
    }

    public static void main(String[] args) {
        SpringApplication.run(AiApplication.class, args);
    }


}
