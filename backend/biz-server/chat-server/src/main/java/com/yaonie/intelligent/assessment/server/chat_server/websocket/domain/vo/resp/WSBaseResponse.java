package com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp;


import lombok.Data;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 14:04
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WSBaseResponse
 * @Project backend
 * @Description :
 */
@Data
public class WSBaseResponse<T> {
    /**
     * @see com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.enums.WSRespTypeEnum
     */
    private Integer type;
    private T data;
}
