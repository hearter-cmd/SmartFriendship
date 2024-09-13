package com.yaonie.intelligent_assessment_server.chat_server.controller;
import com.yaonie.intelligent_assessment_server.chat_server.entity.enums.ResponseCodeEnum;
import com.yaonie.intelligent_assessment_server.chat_server.entity.vo.ResponseVO;
import com.yaonie.intelligent_assessment_server.exception.BusinessException;


/**
 * @author 77160
 */
public class ABaseController {

    protected <T> ResponseVO<T> getSuccessResponseVO(T t) {
        ResponseVO<T> responseVO = new ResponseVO<>();
        responseVO.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVO.setMessage(ResponseCodeEnum.CODE_200.getMsg());
        responseVO.setData(t);
        return responseVO;
    }

    protected <T> ResponseVO<T> getBusinessErrorResponseVO(BusinessException e, T t) {
        ResponseVO<T> vo = new ResponseVO<>();
        if (e.getCode() == null) {
            vo.setCode(ResponseCodeEnum.CODE_600.getCode());
        } else {
            vo.setCode(e.getCode());
        }
        vo.setMessage(e.getMessage());
        vo.setData(t);
        return vo;
    }

    protected <T> ResponseVO<T> getServerErrorResponseVO(T t) {
        ResponseVO<T> vo = new ResponseVO<T>();
        vo.setCode(ResponseCodeEnum.CODE_500.getCode());
        vo.setMessage(ResponseCodeEnum.CODE_500.getMsg());
        vo.setData(t);
        return vo;
    }
}
