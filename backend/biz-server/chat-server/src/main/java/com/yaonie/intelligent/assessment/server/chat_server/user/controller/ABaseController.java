package com.yaonie.intelligent.assessment.server.chat_server.user.controller;

import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;


/**
 * @author 77160
 */
public class ABaseController {

    protected <T> BaseResponse<T> getSuccessResponseVO(T t) {
        return ResultUtils.success(t);
    }

    protected <T> BaseResponse<?> getBusinessErrorResponseVO(BusinessException e, T t) {
        return ResultUtils.error(600, e.getMessage());
    }
}
