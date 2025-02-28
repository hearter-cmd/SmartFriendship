package com.yaonie.intelligent.assessment.ai.service.impl;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.Image;
import org.springframework.ai.image.ImageGeneration;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.ai.zhipuai.ZhiPuAiImageModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-08
 */
@Service
public class ZhiPuService {

    @Resource
    private ZhiPuAiChatModel chatClient;

    @Resource
    private ZhiPuAiImageModel zhiPuAiImageModel;

//    @Resource
//    private ZhiPuAi imageClient;

    /**
     * 获取AI回复的信息
     *
     * 根据提供的系统信息和用户信息，通过调用智谱AI聊天模型获取回复信息。
     *
     * @param systemString 系统信息，用于初始化SystemMessage对象
     * @param userString   用户输入的信息，用于初始化UserMessage对象
     * @return 返回AI根据系统信息和用户信息生成的回复信息
     */
    public String getMessage(String systemString, String userString) {
        SystemMessage systemMessage = new SystemMessage(systemString);
        UserMessage userMessage = new UserMessage(userString);
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        ChatResponse call = chatClient.call(prompt);
        return call.getResult().getOutput().getContent();
    }

    public Flux<String> getMessageByStream(String systemString, String userString) {
        SystemMessage systemMessage = new SystemMessage(systemString);
        UserMessage userMessage = new UserMessage(userString);
        Prompt prompt = new Prompt(
                List.of(systemMessage, userMessage)
        );
        Flux<String> result = chatClient.stream(prompt)
                .map(item -> item.getResult().getOutput().getContent());
        return result;
    }

    /**
     * 根据用户输入获取图片列表
     *
     * @param userString 用户输入的内容，用于生成图片提示
     * @return 返回一个包含图片的列表，每个图片是根据用户输入内容生成的
     */
    public List<Image> getImage(String userString) {
        // 构建Prompt
        ImagePrompt userMessage = new ImagePrompt(userString, null);

        // 发送
        ImageResponse call = zhiPuAiImageModel.call(userMessage);
        List<Image> results = call.getResults().stream().map(ImageGeneration::getOutput).toList();

        return results;
    }

    /**
     * 根据输入的消息获取图片的URL列表
     *
     * @param message 用户输入的消息，用于生成图片提示
     * @return 返回一个包含图片URL的列表，每个URL对应一张根据输入消息生成的图片
     */
    public List<String> getImageUrl(String message) {
        // 构建Prompt
        ImagePrompt userMessage = new ImagePrompt(message, null);

        // 发送
        ImageResponse call = zhiPuAiImageModel.call(userMessage);
        List<String> results = call.getResults().stream().map(a -> a.getOutput().getUrl()).toList();

        return results;
    }


}
