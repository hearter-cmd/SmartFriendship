package com.yaonie.intelligent.assessment.ai.domain.model.dto.dict;

import com.yaonie.intelligent.assessment.server.common.model.model.common.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询应用请求
 *
 * @author 77160
 */
@Schema(name = "模型类型查询DTO")
@EqualsAndHashCode(callSuper = true)
@Data
public class ModelTypeDictQueryRequest extends PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @SchemaProperty(name = "模型名称")
    private String modelName;

    @SchemaProperty(name = "模型的ApiKey")
    private String modelApiKey;
}