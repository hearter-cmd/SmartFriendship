package com.yaonie.intelligent.assessment.ai.service.impl;


import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusAddDto;
import com.yaonie.intelligent.assessment.ai.service.MatchService;
import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.system.service.UserService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.VectorStoreChatMemoryAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.MilvusVectorStore;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-02-04
 */
@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MilvusVectorStore milvusVectorStore;
    private final UserService userService;
    private ZhiPuAiChatModel zhiPuAiChatModel;

    @Override
    public List<User> doMatch() {
        String keywords = initKeyWords("打豆豆");

        List<Document> documents = milvusVectorStore.similaritySearch(keywords);
        List<String> list = documents.stream().map(Document::getId).toList();
        List<User> result = list.isEmpty() ? null : userService.lambdaQuery().in(User::getId, list).last("limit 10").list();
        if (result == null || result.isEmpty()) {
            result = userService.lambdaQuery().last("limit 10").list();
        }
        return result;
    }

    @Override
    public void doAdd(MilvusAddDto milvusAddDto) {
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        if (Objects.isNull(securityUser)) {
            return ;
        }
        List<String> tags = milvusAddDto.getTags();
        String keyWords = initKeyWords(String.join(",", tags));
        milvusVectorStore.add(List.of(new Document(securityUser.getUserId().toString(), keyWords, new HashMap<>())));
    }

    public void test() {
        VectorStoreChatMemoryAdvisor testAdvisor = new VectorStoreChatMemoryAdvisor(milvusVectorStore, "打豆豆", 2);
        ChatClient.builder(zhiPuAiChatModel).defaultAdvisors(item -> {
            item.advisors(testAdvisor);
        });
    }

    // TODO 测试用
    @NotNull(value = "请输入关键字或完善个人信息")
    private String initKeyWords(String keywords) {
        // 拼接关键字
        User user = UserHolder.getUser();
        String sex = '0' == user.getSex() ? "男" : "女";
        String area = user.getAreaName();
        String tags = /*StringUtils.isBlank(keywords) ? user.getTags() :*/ keywords;
        String result = String.join(",", sex, area, tags);
        return result;
    }
}
