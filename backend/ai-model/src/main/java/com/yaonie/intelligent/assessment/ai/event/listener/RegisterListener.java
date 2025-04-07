package com.yaonie.intelligent.assessment.ai.event.listener;


import com.yaonie.intelligent.assessment.server.common.model.event.RegisterEvent;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.MilvusVectorStore;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-23
 */
@Component
public class RegisterListener {
    @Resource
    private MilvusVectorStore milvusVectorStore;

    @EventListener
    public void onRegisterEvent(RegisterEvent event) {
        String userId = event.getUserId();
        List<String> tags = event.getTags();
        String keyWords = initKeyWords(event.getSex(), event.getAreaName(), String.join(",", event.getTags()));
        milvusVectorStore.add(List.of(new Document(userId, keyWords, new HashMap<>())));
        System.out.println("Received register event for user: " + userId + ", tags: " + tags);
    }

    // TODO 测试用
    @NotNull(value = "请输入关键字或完善个人信息")
    private String initKeyWords(String sex, String area, String keywords) {
        // 拼接关键字
        return String.join(",", sex, area, keywords);
    }

}
