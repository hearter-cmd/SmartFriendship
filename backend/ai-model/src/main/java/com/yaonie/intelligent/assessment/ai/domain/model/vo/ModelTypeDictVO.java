package com.yaonie.intelligent.assessment.ai.domain.model.vo;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-09
 */
@Data
public class ModelTypeDictVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String modelName;

    private String modelApiKey;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;

    private String deleteBy;

    private Date deleteTime;
}
