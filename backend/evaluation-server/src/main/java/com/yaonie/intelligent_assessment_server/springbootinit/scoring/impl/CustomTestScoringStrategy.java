package com.yaonie.intelligent_assessment_server.springbootinit.scoring.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yaonie.intelligent_assessment_server.model.dto.question.QuestionContextDto;
import com.yaonie.intelligent_assessment_server.model.entity.App;
import com.yaonie.intelligent_assessment_server.model.entity.Question;
import com.yaonie.intelligent_assessment_server.model.entity.ScoringResult;
import com.yaonie.intelligent_assessment_server.model.entity.UserAnswer;
import com.yaonie.intelligent_assessment_server.springbootinit.scoring.ScoringStrategy;
import com.yaonie.intelligent_assessment_server.springbootinit.service.QuestionService;
import com.yaonie.intelligent_assessment_server.springbootinit.service.ScoringResultService;
import jakarta.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-18 22:56
 * @Author 武春利
 * @CreateTime 2024-08-18
 * @ClassName CustomTestScoringStrategy
 * @Project backend
 * @Description : 自定义测评策略
 */
@ScoringStrategyConfig(appType = 1, scoringType = 0)
public class CustomTestScoringStrategy implements ScoringStrategy {
    @Resource
    private QuestionService questionService;

    @Resource
    private ScoringResultService scoringResultService;

    @Override
    public UserAnswer doScore(List<String> choices, App app) throws Exception {
        // 1. 通过用户ID获取 用户答案, 通过应用获取问
        Long appId = app.getId();

        Question question = questionService.getOne(
                Wrappers.lambdaQuery(Question.class)
                        .eq(Question::getAppId, appId)
        );
        List<ScoringResult> scoringResultList = scoringResultService.list(
                Wrappers.lambdaQuery(ScoringResult.class)
                        .eq(ScoringResult::getAppId, appId)
        );
        // 2. 统计用户每个选项对应的属性个数, 如 E: 3, I: 2
        Map<String, Integer> scoreMap = new HashMap<>();
        List<QuestionContextDto> questionContent = JSONUtil.toList(question.getQuestionContent(), QuestionContextDto.class);
        // 遍历题目列表
        for (int i = 0; i < choices.size(); i++) {
            QuestionContextDto questionContentDTO = questionContent.get(i);
            String answer = choices.get(i);
            // 遍历题目中的选项
            for (QuestionContextDto.Option option : questionContentDTO.getOptions()) {
                // 如果答案和选项的key匹配
                if (option.getKey().equals(answer)) {
                    String result = option.getResult();

                    // 如果result属性不在optionCount中，初始化为0
                    if (!scoreMap.containsKey(result)) {
                        scoreMap.put(result, 0);
                    }

                    scoreMap.put(result, scoreMap.get(result) + 1);
                    break;
                }
            }
        }
        // 3. 遍历每种评分结果, 计算哪个结果的得分最高
        int maxScore = 0;
        ScoringResult maxScoreResult = scoringResultList.get(0);
        for (ScoringResult scoringResult : scoringResultList) {
            List<String> resultProp = JSONUtil.toList(scoringResult.getResultProp(), String.class);
            int score = resultProp.stream()
                    .mapToInt(prop -> scoreMap.getOrDefault(prop, 0))
                    .sum();

            if (score > maxScore) {
                maxScore = score;
                maxScoreResult = scoringResult;
            }
        }
        // 4. 通过得分最高的评分结果, 获取对应的评分结果

        // 5. 构造答案对象返回结果
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoreResult.getId());
        userAnswer.setResultName(maxScoreResult.getResultName());
        userAnswer.setResultDesc(maxScoreResult.getResultDesc());
        userAnswer.setResultPicture(maxScoreResult.getResultPicture());

        return userAnswer;
    }
}
