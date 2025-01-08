package com.yaonie.intelligent.assessment.server.common.model.model.dto.statistic;


import lombok.Data;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-08-31 16:40
 * @Author 武春利
 * @CreateTime 2024-08-31
 * @ClassName AppAnswerResultCountDTO
 * @Project backend
 * @Description : 应用回答结果计数
 */
@Data
public class AppAnswerResultCountDTO {
    private String resultName;
    private Long resultCount;
}
