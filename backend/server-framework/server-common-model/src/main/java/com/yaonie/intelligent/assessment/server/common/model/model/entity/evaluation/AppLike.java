package com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation;

import lombok.Data;

import java.util.Date;

/**
 * app点赞信息(AppLike)表实体类
 *
 * @author makejava
 * @since 2024-10-13 18:10:56
 */
@Data
@SuppressWarnings("serial")
public class AppLike {
    //AppId
    private Long appId;
    //用户Id
    private Long userId;
    //创建时间
    private Date createTime;

}

