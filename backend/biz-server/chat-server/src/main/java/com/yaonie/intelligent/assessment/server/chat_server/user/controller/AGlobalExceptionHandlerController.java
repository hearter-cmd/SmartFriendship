package com.yaonie.intelligent.assessment.server.chat_server.user.controller;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.ResponseCodeEnum;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;



/**
 * @author 77160
 */
@RestControllerAdvice
public class AGlobalExceptionHandlerController extends ABaseController {

    private static final Logger logger = LoggerFactory.getLogger(AGlobalExceptionHandlerController.class);

    @ExceptionHandler(value = Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {
        logger.error("请求错误，请求地址{},错误信息:", request.getRequestURL(), e);
        BaseResponse<?> ajaxResponse;
        //404
        if (e instanceof BusinessException biz) {
            //业务错误
            ajaxResponse = ResultUtils.error(ErrorCode.OPERATION_ERROR, biz.getMessage());
        } else if (e instanceof BindException|| e instanceof MethodArgumentTypeMismatchException) {
            //参数类型错误
            ajaxResponse = ResultUtils.error(ErrorCode.PARAMS_ERROR);
        } else if (e instanceof DuplicateKeyException) {
            //主键冲突
            ajaxResponse = ResultUtils.error(ResponseCodeEnum.CODE_601.getCode(), ResponseCodeEnum.CODE_601.getMsg());
        } else {
            ajaxResponse = ResultUtils.error(500, e.getMessage());
        }
        return ajaxResponse;
    }
}
