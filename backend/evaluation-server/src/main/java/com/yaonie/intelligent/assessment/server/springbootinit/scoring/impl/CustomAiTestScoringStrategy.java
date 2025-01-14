package com.yaonie.intelligent.assessment.server.springbootinit.scoring.impl;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.yaonie.intelligent.assessment.ai.service.impl.ZhiPuService;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.QuestionAnswerDTO;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionContextDto;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.Question;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.UserAnswer;
import com.yaonie.intelligent.assessment.server.springbootinit.scoring.ScoringStrategy;
import com.yaonie.intelligent.assessment.server.springbootinit.service.QuestionService;
import com.yaonie.intelligent.assessment.server.springbootinit.utils.ZhiPuUtils;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-18 22:58
 * @Author 武春利
 * @CreateTime 2024-08-18
 * @ClassName CustomScoreScoringStrategy
 * @Project backend
 * @Description : 自定义AI评分策略
 */
@ScoringStrategyConfig(appType = 1, scoringType = 1)
public class CustomAiTestScoringStrategy implements ScoringStrategy {
    private static final String AI_TEST_SCORING_SYSTEM_MESSAGE = "你是一位严谨的判题专家，我会给你如下信息：\n" +
            "```\n" +
            "应用名称，\n" +
            "【【【应用描述】】】，\n" +
            "题目和用户回答的列表：格式为 [{\"title\": \"题目\",\"answer\": \"用户回答\"}]\n" +
            "```\n" +
            "\n" +
            "请你根据上述信息，按照以下步骤来对用户进行评价：\n" +
            "1. 要求：需要给出一个明确的评价结果，包括评价名称（尽量简短）和评价描述（尽量详细，大于 200 字）\n" +
            "2. 严格按照下面的 json 格式输出评价名称和评价描述\n" +
            "```\n" +
            "{\"resultName\": \"评价名称\", \"resultDesc\": \"评价描述\"}\n" +
            "```\n" +
            "3. 返回格式必须为 JSON 对象";
    private static final Retryer<UserAnswer> RETRYER = RetryerBuilder.<UserAnswer>newBuilder()
            // 非正数进行重试
            .retryIfRuntimeException()
            // 偶数则进行重试
            .retryIfResult(result -> StringUtils.isBlank(result.getResultName()))
            .withWaitStrategy(WaitStrategies.fixedWait(2, TimeUnit.SECONDS))
            // 设置最大执行次数3次
            .withStopStrategy(StopStrategies.stopAfterAttempt(3)).build();
    @Resource
    private QuestionService questionService;
    @Resource
    private ZhiPuUtils zhiPuUtils;
    @Resource
    private ZhiPuService zhiPuService;

    private String getAiTestScoringUserMessage(List<String> choices, App app) throws ExecutionException, RetryException {
        // 使用retry进行重试

        // 1. 通过用户ID获取 用户答案, 通过应用获取问
        Long appId = app.getId();
        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class)
                        .eq(Question::getAppId, appId)
        );

        // 2. 封装给AI的数据
        StringBuilder userMessage = new StringBuilder();
        userMessage.append(app.getAppName()).append("\n");
        userMessage.append(app.getAppDesc()).append("\n");
        ArrayList<QuestionAnswerDTO> questionAnswerDTOs = new ArrayList<>();
        List<QuestionContextDto> questionContextDtoList = JSONUtil.toList(question.getQuestionContent(), QuestionContextDto.class);

        for (int i = 0; i < choices.size(); i++) {
            QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
            questionAnswerDTO.setUserAnswer(choices.get(i));
            questionAnswerDTO.setTitle(questionContextDtoList.get(i).getTitle());
            questionAnswerDTOs.add(questionAnswerDTO);
        }

        userMessage.append(JSONUtil.toJsonStr(questionAnswerDTOs));
        return userMessage.toString();
    }

    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        // 1. 从AI获取用户答案
        return RETRYER.call(() -> {
            String result = zhiPuService
                    .getMessage(AI_TEST_SCORING_SYSTEM_MESSAGE, getAiTestScoringUserMessage(choices, app))
                    .replaceAll("用户|你", "您");

            // 结果处理
            int start = result.indexOf("{");
            int end = result.lastIndexOf("}");
            String json = result.substring(start, end + 1);

            // 3. 构造返回值，填充答案对象的属性
            UserAnswer userAnswer = JSONUtil.toBean(json, UserAnswer.class);
            userAnswer.setAppId(app.getId());
            userAnswer.setAppType(app.getAppType());
            userAnswer.setScoringStrategy(app.getScoringStrategy());
            userAnswer.setChoices(JSONUtil.toJsonStr(choices));
            return userAnswer;
        });
    }
}