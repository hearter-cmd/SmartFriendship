package com.yaonie.intelligent.assessment.server.search_server.facade;


import com.yaonie.intelligent.assessment.server.common.model.model.vo.SearchVO;
import com.yaonie.intelligent.assessment.server.search_server.datasource.SearchDataSource;
import com.yaonie.intelligent.assessment.server.search_server.model.dto.search.SearchRequest;
import com.yaonie.intelligent.assessment.server.search_server.model.enums.SearchTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-08 1:56
 * @Author 武春利
 * @CreateTime 2024-09-08
 * @ClassName SearchFacade
 * @Project backend
 * @Description : 搜索门面
 */
@Component
@Slf4j
public class SearchFacade {

    /**
     * 类似于统一注册
     */
    @Resource
    Map<String, SearchDataSource> searchDataSourceMap;

    /**
     * 搜索通用方法
     *
     * @param searchRequest 搜索参数
     * @return 搜索结果
     */
    public SearchVO doSearch(SearchRequest searchRequest) {
        SearchTypeEnum searchType = searchRequest.getSearchType();
        SearchVO searchVO = new SearchVO();
        if (searchType == null) {
            for (String s : searchDataSourceMap.keySet()) {
                SearchDataSource searchDataSource = searchDataSourceMap.get(s);
                searchDataSource.doSearch(searchRequest, searchVO);
            }
        } else {
            log.info(searchType.getDataSource());
            SearchDataSource searchDataSource = searchDataSourceMap.get(searchType.getDataSource());
            searchDataSource.doSearch(searchRequest, searchVO);
        }
        return searchVO;
    }
}
