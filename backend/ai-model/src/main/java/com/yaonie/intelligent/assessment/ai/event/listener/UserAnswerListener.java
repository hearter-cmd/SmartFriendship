package com.yaonie.intelligent.assessment.ai.event.listener;


import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusAddDto;
import com.yaonie.intelligent.assessment.ai.service.MatchService;
import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.event.UserAnswerEvent;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.UserAnswer;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.server.common.util.StringUtils;
import jakarta.annotation.Resource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-04-05
 */
@Component
public class UserAnswerListener {

    @Resource
    private MatchService matchService;

    @EventListener(classes = {UserAnswerEvent.class})
    public void updateTag(UserAnswerEvent event) {
        UserAnswer userAnswer = event.getUserAnswer();
        String resultName = userAnswer.getResultName();
        Long userId = SecurityUtils.getUserId();
        ThrowUtils.throwIf(StringUtils.isBlank(resultName) || Objects.isNull(userId), ErrorCode.PARAMS_ERROR, "评分失败，更新用户信息失败");
        // 更新Milvus中的数据
        MilvusAddDto milvusAddDto = new MilvusAddDto();
        String[] tags = UserHolder.getUser().getTags().split(",");
        List<String> list = new java.util.ArrayList<>(Arrays.stream(tags).toList());
        list.add(resultName);
        milvusAddDto.setTags(list);
        matchService.refresh(milvusAddDto);
    }


}
