package com.yaonie.intelligent.assessment.server.springbootinit.utils;


import com.yaonie.intelligent.assessment.ai.service.impl.ZhiPuService;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.springbootinit.properties.ZhiPuAi;
import com.zhipu.oapi.ClientV4;
import com.zhipu.oapi.Constants;
import com.zhipu.oapi.service.v4.model.ChatCompletionRequest;
import com.zhipu.oapi.service.v4.model.ChatMessage;
import com.zhipu.oapi.service.v4.model.ChatMessageRole;
import com.zhipu.oapi.service.v4.model.ModelApiResponse;
import com.zhipu.oapi.service.v4.model.ModelData;
import io.reactivex.Flowable;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-22 19:00
 * @Author 武春利
 * @CreateTime 2024-08-22
 * @ClassName ZhiPuUtils
 * @Project backend
 * @Description : 智普 调用封装
 */
@Component
@Slf4j
public class ZhiPuUtils {
    @Resource
    private ClientV4 clientV4;

    @Resource
    private ZhiPuAi zhiPuAi;

    @Resource
    private ZhiPuService zhiPuService;

    /**
     * 异步请求(答案稳定)
     * @param systemMessage 系统消息
     * @param userMessage 用户消息
     * @return AI返回的消息
     */
    public String doAsyncStableRequest(String systemMessage, String userMessage, Float temperature ) {
        return doRequest(systemMessage, userMessage, Boolean.FALSE, temperature);
    }

    /**
     * 异步请求(答案不稳定)
     * @param systemMessage 系统消息
     * @param userMessage 用户消息
     * @return AI返回的消息
     */
    public String doAsyncUnstableRequest(String systemMessage, String userMessage, Float temperature) {
        return doRequest(systemMessage, userMessage, Boolean.FALSE, temperature);
    }

    /**
     * 同步请求 请求知普AI的封装
     * @param systemMessage 系统消息
     * @param userMessage 用户消息
     * @param temperature 创造性, 值越大, 越随机     范围[0,1]
     * @return AI返回的消息
     */
    public String doRequest(String systemMessage, String userMessage, Float temperature) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        messages.add(systemChatMessage);

        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        messages.add(userChatMessage);
        return doRequest(messages, Boolean.FALSE, temperature);
    }

    /**
     * 请求知普AI的封装
     * @param systemMessage 系统消息
     * @param userMessage 用户消息
     * @param stream 是否流式输出
     * @param temperature 创造性, 值越大, 越随机     范围[0,1]
     * @return AI返回的消息
     */
    public String doRequest(String systemMessage, String userMessage, Boolean stream, Float temperature) {
        // 构建消息
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        messages.add(systemChatMessage);

        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        messages.add(userChatMessage);
        // 调用重载的方法, 实现业务
        return doRequest(messages, stream, temperature);
    }

    /**
     * 请求知普AI的封装, 更加多的自定义
     * @param messages 消息集合
     * @param stream 是否流式输出
     * @param temperature 创造性, 值越大, 越随机     范围[0,1]
     * @return AI返回的消息
     */
    public String doRequest(List<ChatMessage> messages, Boolean stream, Float temperature) {
        // 构建请求
        // 这是请求的时候需要发送的语句集合
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                // 模型选择
                .model(Constants.ModelChatGLM4V)
                // 系统消息
                .invokeMethod(Constants.invokeMethod)
                // 是否流式输出
                .stream(stream)
                // 随机程度
                .temperature(temperature != null ? temperature : zhiPuAi.getTemperature())
                // 消息
                .messages(messages)
                .build();
        try {
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return invokeModelApiResp.getData().getChoices().get(0).getMessage().getContent().toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    /**
     * 流式返回数据
     * @param systemMessage 系统消息
     * @param userMessage 用户消息
     * @param temperature 灵活性
     * @return flowable流, 用于式返回数据
     */
    public Flowable<ModelData> doStreamRequest(String systemMessage, String userMessage, Float temperature) {
        // 构建消息
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemChatMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage);
        messages.add(systemChatMessage);

        ChatMessage userChatMessage = new ChatMessage(ChatMessageRole.USER.value(), userMessage);
        messages.add(userChatMessage);

        // 调用重载的方法, 实现业务
        return doStreamRequest(messages, temperature);
    }

    /**
     * 请求知普AI的封装, 流式返回数据
     * @param messages 消息集合
     * @param temperature 创造性, 值越大, 越随机     范围[0,1]
     * @return AI返回的消息
     */
    public Flowable<ModelData> doStreamRequest(List<ChatMessage> messages, Float temperature) {
        // 构建请求
        // 这是请求的时候需要发送的语句集合
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                // 模型选择
                .model(Constants.ModelChatGLM4V)
                // 系统消息
                .invokeMethod(Constants.invokeMethod)
                // 是否流式输出
                .stream(Boolean.TRUE)
                // 消息
                .messages(messages)
                .build();
        try {
            ModelApiResponse invokeModelApiResp = clientV4.invokeModelApi(chatCompletionRequest);
            return invokeModelApiResp.getFlowable();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, e.getMessage());
        }
    }

    public String doDrawPhoto(String message) {
        String imageUrl = zhiPuService.getImageUrl(message).get(0);
        log.info(imageUrl);
        return imageUrl;
    }
}
