package com.yaonie.intelligent.assessment.server.search_server.model.dto.search;


import com.yaonie.intelligent.assessment.server.common.model.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-08 6:15
 * @Author 武春利
 * @CreateTime 2024-09-08
 * @ClassName DoSearchRequest
 * @Project backend
 * @Description : 搜索请求细化
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DoSearchRequest extends PageRequest implements Serializable {
    /**
     * 关键词
     */
    private String searchText;

    /**
     * 序列化标志
     */
    @Serial
    private static final long serialVersionUID = 1L;
}
