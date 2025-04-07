package com.yaonie.intelligent.assessment.server.springbootinit;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.util.ArrayList;
import java.util.List;


/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-22 11:49
 * @Author 武春利
 * @CreateTime 2024-08-22
 * @ClassName ZhiPuAiTest
 * @Project backend
 * @Description :
 */

/**
 * 这里需要禁用coroutine agent
 */
@SpringBootTest
@SpringJUnitConfig
@SpringJUnitWebConfig
public class ZhiPuAiTest {

    @Resource
    private ClientV4 clientV4;

    @Test
    void test1() throws JsonProcessingException {
        /**
         * 同步调用
         */
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage chatMessage = new ChatMessage(ChatMessageRole.USER.value(), "作为一名营销专家，请为智谱开放平台创作一个吸引人的slogan");
        messages.add(chatMessage);

        String requestIdTemplate = "requestId_%d";
        String requestId = String.format(requestIdTemplate, System.currentTimeMillis());

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(Constants.ModelChatGLM4)
                .stream(Boolean.FALSE)
                .invokeMethod(Constants.invokeMethod)
                .messages(messages)
                .requestId(requestId)
                .build();
        ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
        System.out.println("model output:" + invokeModelApiResp.toString());
    }

    @Test
    void test2() {

    }
}
