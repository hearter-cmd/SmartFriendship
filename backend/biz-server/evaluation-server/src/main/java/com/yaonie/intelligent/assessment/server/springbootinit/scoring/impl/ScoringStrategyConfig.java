package com.yaonie.intelligent.assessment.server.springbootinit.scoring.impl;


import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-19 10:50
 * @Author 武春利
 * @CreateTime 2024-08-19
 * @ClassName ScoringStrategyConfig
 * @Project backend
 * @Description :
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ScoringStrategyConfig {
    /**
     * 应用类型
     * 1 - 测评类, 0 - 得分类
     */
    int appType();

    /**
     * 评分类型
     * 0 - 自定义评分, 1 - AI评分
     */
    int scoringType();

}
