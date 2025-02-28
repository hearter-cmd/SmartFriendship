package com.yaonie.intelligent.assessment.server.chat_server.websocket.domain.vo.resp;


import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 14:26
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WSLoginSuccess
 * @Project backend
 * @Description : 
 */
@Data
@Builder
public class WSLoginSuccess {
    private String id;
    private String userName;
    private String userAvatar;
    private String userRole;
    private String userProfile;
    private Date updateTime;
    private Date createTime;
}
