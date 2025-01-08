package com.yaonie.intelligent.assessment.server.common.model.model.dto.file;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 文件上传请求
 * @author yaonie
 */
@Data
public class UploadFileRequest implements Serializable {

    /**
     * 业务
     */
    private String biz;

    @Serial
    private static final long serialVersionUID = 1L;
}