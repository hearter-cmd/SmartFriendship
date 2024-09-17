package com.yaonie.intelligent.assessment.server.search_server.datasource;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.SearchVO;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import com.yaonie.intelligent.assessment.server.search_server.model.dto.search.SearchRequest;
import com.yaonie.intelligent.assessment.server.search_server.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-08 1:56
 * @Author 武春利
 * @CreateTime 2024-09-08
 * @ClassName UserSearchDataSourceImpl
 * @Project backend
 * @Description : 用户搜索数据源实现类
 */
@Component
public class UserSearchDataSourceImpl implements SearchDataSource<UserVO> {
    @Resource
    private UserService userService;

    @Override
    public List<UserVO> doSearch(SearchRequest searchRequest, SearchVO searchVO) {
        Page<User> page = new Page<>(searchRequest.getCurrent(), searchRequest.getPageSize());
        Page<User> res = userService.page(page,
                getSearchWrapper(searchRequest));
        List<UserVO> collect = res.getRecords().stream().map(item -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(item, userVO);
            return userVO;
        }).collect(Collectors.toList());
        searchVO.setUserVOList(collect);
        return collect;
    }

    public QueryWrapper<User> getSearchWrapper(SearchRequest searchRequest) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("userName",  searchRequest.getSearchText())
                .or()
                .like("userAccount", searchRequest.getSearchText());
        return queryWrapper;
    }
}
