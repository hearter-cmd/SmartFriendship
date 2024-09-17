package com.yaonie.intelligent.assessment.server.search_server.model.dto.search;


import com.yaonie.intelligent.assessment.server.common.model.model.common.PageRequest;
import com.yaonie.intelligent.assessment.server.search_server.model.enums.SearchTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 18:05
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName SearchRequest
 * @Project backend
 * @Description : 搜索请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {
    /**
     * 关键词
     */
    private String searchText;

    /**
     * 查询类型
     */
    private SearchTypeEnum searchType;

    /**
     * 序列化标志
     */
    @Serial
    private static final long serialVersionUID = 1L;
}
