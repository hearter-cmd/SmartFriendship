package com.yaonie.intelligent.assessment.server.search_server.datasource;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.server.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.model.vo.AppVO;
import com.yaonie.intelligent.assessment.server.model.vo.SearchVO;
import com.yaonie.intelligent.assessment.server.search_server.model.dto.search.SearchRequest;
import com.yaonie.intelligent.assessment.server.search_server.service.AppService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-08 1:56
 * @Author 武春利
 * @CreateTime 2024-09-08
 * @ClassName AppSearchDataSourceImpl
 * @Project backend
 * @Description : TODO
 */
@Component
public class AppSearchDataSourceImpl implements SearchDataSource<AppVO> {
    @Resource
    private AppService appService;

    @Override
    public List<AppVO> doSearch(SearchRequest searchRequest, SearchVO searchVO) {
        Page<App> page = new Page<>(searchRequest.getCurrent(), searchRequest.getPageSize());
        Page<App> res = appService.page(page,
                getSearchWrapper(searchRequest));
        List<AppVO> collect = res.getRecords().stream().map(AppVO::objToVo).collect(Collectors.toList());
        searchVO.setAppVOList(collect);
        return collect;
    }

    public QueryWrapper<App> getSearchWrapper(SearchRequest searchRequest) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("appName", searchRequest.getSearchText())
                .or()
                .like("appDesc", searchRequest.getSearchText());
        return queryWrapper;
    }

}
