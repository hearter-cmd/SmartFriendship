package com.yaonie.intelligent_assessment_server.springbootinit.scoring;


import com.yaonie.intelligent_assessment_server.model.entity.App;
import com.yaonie.intelligent_assessment_server.model.entity.UserAnswer;

import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-18 21:25
 * @Author 武春利
 * @CreateTime 2024-08-18
 * @ClassName AppStrategy
 * @Project backend
 * @Description : 评分策略
 */
public interface ScoringStrategy {

    /**
     * 执行评分擦欧总
     * @param choices 选项集合
     * @param app 应用实例
     * @return 评分结果封装入用户回答视图, 并返回
     */
    UserAnswer doScore(List<String> choices, App app) throws Exception;
}
