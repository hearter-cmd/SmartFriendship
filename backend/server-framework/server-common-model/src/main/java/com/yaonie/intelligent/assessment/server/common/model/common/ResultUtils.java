package com.yaonie.intelligent.assessment.server.common.model.common;

/**
 * 返回工具类
 *
 * @author yaonie
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param <T>  数据类型
     * @return 成功信息响应对象
     */
    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(0, null, "ok");
    }

    /**
     * 成功
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 成功信息响应对象
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 错误码枚举
     * @return 错误信息
     */
    public static BaseResponse<Object> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code    错误码
     * @param message 错误信息
     * @return 错误信息响应对象
     */
    public static BaseResponse<Object> error(int code, String message) {
        return new BaseResponse<>(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode 错误码枚举
     * @return 错误信息响应对象
     */
    public static BaseResponse<Object> error(ErrorCode errorCode, String message) {
        return new BaseResponse<>(errorCode.getCode(), null, message);
    }
}
