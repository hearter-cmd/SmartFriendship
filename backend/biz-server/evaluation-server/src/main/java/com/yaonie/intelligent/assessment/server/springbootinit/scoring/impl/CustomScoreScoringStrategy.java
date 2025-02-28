package com.yaonie.intelligent.assessment.server.springbootinit.scoring.impl;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.question.QuestionContextDto;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.Question;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.ScoringResult;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.UserAnswer;
import com.yaonie.intelligent.assessment.server.springbootinit.scoring.ScoringStrategy;
import com.yaonie.intelligent.assessment.server.springbootinit.service.QuestionService;
import com.yaonie.intelligent.assessment.server.springbootinit.service.ScoringResultService;
import jakarta.annotation.Resource;

import java.util.List;
import java.util.Optional;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-18 22:58
 * @Author 武春利
 * @CreateTime 2024-08-18
 * @ClassName CustomScoreScoringStrategy
 * @Project backend
 * @Description : 自定义评分策略
 */
@ScoringStrategyConfig(appType = 0, scoringType = 0)
public class CustomScoreScoringStrategy implements ScoringStrategy {
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
                        .orderByDesc(ScoringResult::getResultScoreRange)
        );
        // 2. 统计用户每个选项对应的属性个数, 如 E: 3, I: 2
        int totalScore = 0;
        List<QuestionContextDto> questionContent = JSONUtil.toList(question.getQuestionContent(), QuestionContextDto.class);
        // 遍历题目列表
        for (int i = 0; i < choices.size(); i++) {
            QuestionContextDto questionContentDTO = questionContent.get(i);
            String answer = choices.get(i);
            // 遍历题目中的选项
            for (QuestionContextDto.Option option : questionContentDTO.getOptions()) {
                // 如果答案和选项的key匹配
                if (option.getKey().equals(answer)) {
                    Integer result = Optional.ofNullable(option.getScore()).orElse(0);
                    totalScore += result;
                    break;
                }
            }
        }
        ScoringResult maxScoreResult = scoringResultList.get(0);
        for (ScoringResult scoringResult : scoringResultList) {
            if (totalScore >= scoringResult.getResultScoreRange()) {
                maxScoreResult = scoringResult;
                break;
            }
        }
        // 3. 封装评分结果, 返回
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setAppId(appId);
        userAnswer.setAppType(app.getAppType());
        userAnswer.setScoringStrategy(app.getScoringStrategy());
        userAnswer.setChoices(JSONUtil.toJsonStr(choices));
        userAnswer.setResultId(maxScoreResult.getId());
        userAnswer.setResultName(maxScoreResult.getResultName());
        userAnswer.setResultDesc(maxScoreResult.getResultDesc());
        userAnswer.setResultPicture(maxScoreResult.getResultPicture());
        userAnswer.setResultScore(totalScore);

        return userAnswer;
    }
}
