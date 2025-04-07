package com.yaonie.intelligent.assessment.ai.service.impl;


import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusAddDto;
import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusGroupAddDto;
import com.yaonie.intelligent.assessment.ai.domain.model.po.CustomDocument;
import com.yaonie.intelligent.assessment.ai.service.GroupInfoService;
import com.yaonie.intelligent.assessment.ai.service.GroupVectorService;
import com.yaonie.intelligent.assessment.ai.service.MatchService;
import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.SecurityUser;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.server.common.util.StringUtils;
import com.yaonie.intelligent.assessment.system.service.UserService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.MilvusVectorStore;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class MatchServiceImpl implements MatchService {

    @Resource
    private MilvusVectorStore milvusVectorStore;
    @Resource
    private UserService userService;
    @Resource
    private ZhiPuAiChatModel zhiPuAiChatModel;
    @Resource
    private GroupVectorService groupVectorService;
    @Autowired
    private GroupInfoService groupInfoService;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public List<UserVO> doMatch() {
        // 判断当前是否登录
        User user = UserHolder.getUser();
        String tags = user.getTags();
//        if (Objects.isNull(tags) || StringUtils.isBlank(tags)) {
//            return userService.lambdaQuery().last("limit 10").list().stream().map(item -> userService.getUserVO(item)).toList();
//        }

        // 初始化匹配关键字
        String keywords = initKeyWords(tags);

        // 进行匹配
        List<Document> documents = milvusVectorStore.similaritySearch(keywords);
        List<String> list = documents.stream().map(Document::getId).toList();

        // 通过获取到的ID，获取用户信息
        List<User> result = list.isEmpty() ? null : userService.lambdaQuery()
                .in(User::getId, list).last("limit 10")
                .eq(User::getEnable, CommonConstant.IS_ENABLE)
                .list();

        // 如果没有匹配的就直接随便取10个
        if (result == null || result.isEmpty()) {
            result = userService.lambdaQuery().last("limit 10").list();
        }

        return result.stream().map(item -> userService.getUserVO(item)).toList();
    }

    @Override
    public void doAdd(MilvusAddDto milvusAddDto) {
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        ThrowUtils.throwIf(Objects.isNull(securityUser), ErrorCode.AUTH_ERROR, "用户未登录");
        List<String> tags = milvusAddDto.getTags();
        String keyWords = initKeyWords(String.join(",", tags));
        milvusVectorStore.add(List.of(new Document(securityUser.getUserId().toString(), keyWords, new HashMap<>())));
    }

    @Override
    public void doAddGroup(MilvusGroupAddDto addDto) {
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        ThrowUtils.throwIf(Objects.isNull(securityUser), ErrorCode.AUTH_ERROR, "用户未登录");
        List<String> tags = addDto.getTags();
        groupVectorService.insert(addDto.getGroupId(), tags);
    }

    @Override
    public List<GroupInfo> doMatchByGroup() {
        // 判断当前是否登录
        User user = UserHolder.getUser();
        String tags = user.getTags();
        if (Objects.isNull(tags) || StringUtils.isBlank(tags)) {
            List<GroupInfo> groupInfoListTop10 = groupInfoService.lambdaQuery().last("limit 10").list();
            return groupInfoListTop10;
        }

        // 初始化匹配关键字
        String keywords = initKeyWords(tags);

        // 进行匹配
        List<CustomDocument> customDocuments = groupVectorService.similaritySearch(keywords);
        List<String> list = customDocuments.stream().map(CustomDocument::getId).toList();

        if (list.isEmpty()) {
            List<GroupInfo> groupInfoListTop10 = groupInfoService.lambdaQuery().last("limit 10").list();
            return groupInfoListTop10;
        }

        List<GroupInfo> result = groupInfoService.lambdaQuery().in(GroupInfo::getGroupId, list).list();
        // 通过获取到的ID，获取群组信息
        if (result == null || result.isEmpty()) {
            result = groupInfoService.lambdaQuery().last("limit 10").list();
        }
        return result;
    }

    @Override
    public void refresh(MilvusAddDto milvusAddDto) {
        SecurityUser securityUser = SecurityUtils.getSecurityUser();
        ThrowUtils.throwIf(Objects.isNull(securityUser), ErrorCode.AUTH_ERROR, "用户未登录");
        milvusVectorStore.delete(List.of(securityUser.getUserId().toString()));
        List<String> tags = milvusAddDto.getTags();
        String keyWords = initKeyWords(String.join(",", tags));
        milvusVectorStore.add(List.of(new Document(securityUser.getUserId().toString(), keyWords, new HashMap<>())));
    }

    @NotNull(value = "请输入关键字或完善个人信息")
    private String initKeyWords(String keywords) {
        // 拼接关键字
        User user = UserHolder.getUser();
        String sex = '0' == user.getSex() ? "男" : "女";
        String area = user.getAreaName();
        String result = String.join(",", sex, area, keywords);
        return result;
    }
}
