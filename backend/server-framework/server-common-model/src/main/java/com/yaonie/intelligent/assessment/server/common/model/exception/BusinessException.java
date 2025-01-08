package com.yaonie.intelligent.assessment.server.common.model.exception;

import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import lombok.Getter;

/**
 * 自定义异常类
 * @author yaonie
 */
@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

}
