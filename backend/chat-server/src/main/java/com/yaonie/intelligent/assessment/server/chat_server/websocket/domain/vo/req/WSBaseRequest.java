package com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.req;


import lombok.Data;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 14:04
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WSBaseRequest
 * @Project backend
 * @Description : 
 */
@Data
public class WSBaseRequest {
    /**
     * 请求类型
     * @see com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums.WSReqTypeEnum
     */
    private Integer type;

    /**
     * 请求携带的数据
     */
    private String data;
}
