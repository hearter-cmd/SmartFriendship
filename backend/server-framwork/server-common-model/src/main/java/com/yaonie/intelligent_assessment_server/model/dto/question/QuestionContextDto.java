package com.yaonie.intelligent_assessment_server.model.dto.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 77160
 */
@Data
public class QuestionContextDto {
    /**
     * 题目标题
     */
    @JsonProperty("title")
    private String title;

    /**
     * 题目选项
     */
    @JsonProperty("options")
    private List<Option> options;

    /**
     * @author 77160
     */
    @Data
    public static class Option {
        // Getters and Setters
        @JsonProperty("result")
        private String result;

        @JsonProperty("score")
        private Integer score;

        @JsonProperty("value")
        private String value;

        @JsonProperty("key")
        private String key;
    }
}