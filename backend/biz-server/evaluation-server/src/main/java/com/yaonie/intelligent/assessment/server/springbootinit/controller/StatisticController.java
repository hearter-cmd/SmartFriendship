package com.yaonie.intelligent.assessment.server.springbootinit.controller;


import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.statistic.AppAnswerCountDTO;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.statistic.AppAnswerResultCountDTO;
import com.yaonie.intelligent.assessment.server.springbootinit.mapper.UserAnswerMapper;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-31 16:43
 * @Author 武春利
 * @CreateTime 2024-08-31
 * @ClassName StatisticController
 * @Project backend
 * @Description : App统计控制器
 */
@RestController
@RequestMapping("/app/statisticInfo")
public class StatisticController {
    @Resource
    private UserAnswerMapper userAnswerMapper;

    /**
     * 获取每个应用用户答题统计 (top 10)
     *
     * @return 用户答题统计
     */
    @GetMapping("answer_count")
    public BaseResponse<List<AppAnswerCountDTO>> getAnswerCount() {
        return ResultUtils.success(userAnswerMapper.doTop10AppAnswerCount());
    }

    /**
     * 获取指定应用用户答题结果统计 (top 10)
     *
     * @param appId 应用id
     * @return 用户答题结果统计
     */
    @GetMapping("answer_result_count/{appId}")
    public BaseResponse<List<AppAnswerResultCountDTO>> getAnswerResultCount(@PathVariable("appId") Long appId) {
        ThrowUtils.throwIf(appId == null, ErrorCode.PARAMS_ERROR);
        if (appId == -1) {
            return ResultUtils.success(null);
        }
        ThrowUtils.throwIf(appId <= 0, ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(userAnswerMapper.doTop10AppAnswerResultCount(appId));
    }
}
