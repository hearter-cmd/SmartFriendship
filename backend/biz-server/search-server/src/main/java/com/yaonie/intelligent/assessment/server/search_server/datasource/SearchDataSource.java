package com.yaonie.intelligent.assessment.server.search_server.datasource;


import com.yaonie.intelligent.assessment.server.common.model.model.vo.SearchVO;
import com.yaonie.intelligent.assessment.server.search_server.model.dto.search.SearchRequest;

import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-08 1:54
 * @Author 武春利
 * @CreateTime 2024-09-08
 * @ClassName SearchDataSource
 * @Project backend
 * @Description : 搜索数据源抽象类
 */
public interface SearchDataSource<T> {
    /**
     * 根据请求参数获取数据
     *
     * @param searchRequest 请求参数
     * @param searchVO      封装到哪里
     * @return 数据列表
     */
    List<T> doSearch(SearchRequest searchRequest, SearchVO searchVO);
}
