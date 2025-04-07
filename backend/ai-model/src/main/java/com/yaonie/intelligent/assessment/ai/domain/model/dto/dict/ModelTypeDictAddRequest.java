package com.yaonie.intelligent.assessment.ai.domain.model.dto.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 创建应用请求
 *
 * @author yaonie
 */
@Schema(name = "模型类型新增DTO")
@Data
public class ModelTypeDictAddRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @SchemaProperty(name = "模型名称")
    private String modelName;

    @SchemaProperty(name = "模型的ApiKey")
    private String modelApiKey;
}