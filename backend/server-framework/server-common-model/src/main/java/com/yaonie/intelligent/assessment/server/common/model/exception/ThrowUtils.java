package com.yaonie.intelligent.assessment.server.common.model.exception;

import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;

/**
 * 抛异常工具类
 * @author yaonie
 */
public class ThrowUtils {

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件表达式
     * @param runtimeException 异常
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件表达式
     * @param errorCode 异常码
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 条件成立则抛异常
     *
     * @param condition 条件表达式
     * @param errorCode 异常码
     * @param message 异常信息
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
