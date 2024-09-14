package com.yaonie.intelligent.assessment.server.chat_server.entity.vo;

import lombok.Data;

@Data
public class ResponseVO<T> {
    private Integer code;
    private String message;
    private T data;
}
