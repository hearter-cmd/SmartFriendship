package com.yaonie.intelligent.assessment.server.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回类
 *
 * @author 77160
 * @param <T>
 */
@Data
public class BaseResponse<T> implements Serializable {

    private Integer code;

    private T data;

    private String message;

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonCreator
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
