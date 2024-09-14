package com.yaonie.intelligent.assessment.server.springbootinit.scoring.impl;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yaonie.intelligent.assessment.server.model.dto.QuestionAnswerDTO;
import com.yaonie.intelligent.assessment.server.model.dto.question.QuestionContextDto;
import com.yaonie.intelligent.assessment.server.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.model.entity.evaluation.Question;
import com.yaonie.intelligent.assessment.server.model.entity.evaluation.UserAnswer;
import com.yaonie.intelligent.assessment.server.springbootinit.scoring.ScoringStrategy;
import com.yaonie.intelligent.assessment.server.springbootinit.service.QuestionService;
import com.yaonie.intelligent.assessment.server.springbootinit.utils.ZhiPuUtils;
import jakarta.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-18 22:58
 * @Author 武春利
 * @CreateTime 2024-08-18
 * @ClassName CustomScoreScoringStrategy
 * @Project backend
 * @Description : TODO
 */
@ScoringStrategyConfig(appType = 1, scoringType = 1)
public class CustomAiTestScoringStrategy implements ScoringStrategy {
    @Resource
    private QuestionService questionService;

    @Resource
    private ZhiPuUtils zhiPuUtils;

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

    private String getAiTestScoringUserMessage(List<String> choices, App app) {
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
        ArrayList<QuestionAnswerDTO> questionAnswerDTOS = new ArrayList<>();
        List<QuestionContextDto> questionContextDtoList = JSONUtil.toList(question.getQuestionContent(), QuestionContextDto.class);

        for (int i = 0;i < choices.size();i ++) {
            QuestionAnswerDTO questionAnswerDTO = new QuestionAnswerDTO();
            questionAnswerDTO.setUserAnswer(choices.get(i));
            questionAnswerDTO.setTitle(questionContextDtoList.get(i).getTitle());
            questionAnswerDTOS.add(questionAnswerDTO);
        }

        userMessage.append(JSONUtil.toJsonStr(questionAnswerDTOS));
        return userMessage.toString();
    }

    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        // 1. 从AI获取用户答案
        String result = zhiPuUtils
                .doAsyncStableRequest(AI_TEST_SCORING_SYSTEM_MESSAGE, getAiTestScoringUserMessage(choices, app), null)
                .replace("用户", "您");

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
    }
}
