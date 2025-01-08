package com.yaonie.intelligent.assessment.server.common.model.model.dto.statistic;


import lombok.Data;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-31 16:15
 * @Author 武春利
 * @CreateTime 2024-08-31
 * @ClassName AppAnswerCountDTO
 * @Project backend
 * @Description : 应用回答计数
 */
@Data
public class AppAnswerCountDTO {
    private Long appId;
    private Long answerCount;
}
