package com.yaonie.intelligent.assessment.server.search_server.controller;


import com.yaonie.intelligent.assessment.server.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.model.vo.SearchVO;
import com.yaonie.intelligent.assessment.server.search_server.facade.SearchFacade;
import com.yaonie.intelligent.assessment.server.search_server.model.dto.search.SearchRequest;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 16:57
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName SearchController
 * @Project backend
 * @Description : TODO
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/")
    public BaseResponse<SearchVO> doSearch(@RequestBody SearchRequest searchRequest) {
        SearchVO searchVO = searchFacade.doSearch(searchRequest);
        return ResultUtils.success(searchVO);
    }
}
