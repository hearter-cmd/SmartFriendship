package com.yaonie.intelligent.assessment.server.chat_server.user.entity.query;


import lombok.Getter;
import lombok.Setter;

/**
 * @author 77160
 */
@Setter
@Getter
public class BaseParam {
    private SimplePage simplePage;
    private Integer pageNo;
    private Integer pageSize;
    private String orderBy;

}
