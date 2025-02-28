package com.yaonie.intelligent.assessment.ai.domain.model.vo;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * AI大模型详细信息(ModelDictDetail)表实体类
 *
 * @author makejava
 * @since 2025-01-09 10:42:40
 */
@Data
public class ModelDictDetailVO implements Serializable {
    //模型代码
    private String modelCode;
    //模型的APIKEY
    private String modelApiKey;
    //创建人
    private String createBy;
    //创建时间
    private Date createTime;
    //更新人
    private String updateBy;
    //更新时间
    private Date updateTime;
    //删除人
    private String deleteBy;
    //删除时间
    private Date deleteTime;
    //是否删除(0: 未删除, 1: 已删除)
    private String isDeleted;

    @Serial
    private static final long serialVersionUID = 1L;
}

