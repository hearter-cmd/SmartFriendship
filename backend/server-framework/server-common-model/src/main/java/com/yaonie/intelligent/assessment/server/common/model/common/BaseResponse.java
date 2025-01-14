package com.yaonie.intelligent.assessment.server.common.model.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    private String originUrl;

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonCreator
    public BaseResponse(int code, T data, String message) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String originUrl = request.getRequestURI();
        this.code = code;
        this.data = data;
        this.message = message;
        this.originUrl = originUrl;
    }

    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
