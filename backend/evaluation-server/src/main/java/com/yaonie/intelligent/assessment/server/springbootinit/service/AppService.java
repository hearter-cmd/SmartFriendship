package com.yaonie.intelligent.assessment.server.springbootinit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.server.common.model.common.DeleteRequest;
import com.yaonie.intelligent.assessment.server.common.model.common.ReviewRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.app.AppEditRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.app.AppQueryRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.app.AppUpdateRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.AppVO;
import jakarta.servlet.http.HttpServletRequest;


/**
 * 应用服务
 *
 * @author 77160
 */
public interface AppService extends IService<App> {

    /**
     * 校验数据
     *
     * @param app 应用
     * @param add 对创建的数据进行校验
     */
    void validApp(App app, boolean add);

    /**
     * 获取查询条件
     *
     * @param appQueryRequest 查询请求
     * @return 查询条件
     */
    QueryWrapper<App> getQueryWrapper(AppQueryRequest appQueryRequest);
    
    /**
     * 获取应用封装
     *
     * @param app 应用
     * @param request 请求
     * @return 应用封装
     */
    AppVO getAppVO(App app, HttpServletRequest request);

    /**
     * 分页获取应用封装
     *
     * @param appPage 分页对象
     * @param request 请求
     * @return 分页应用封装
     */
    Page<AppVO> getAppVOPage(Page<App> appPage, HttpServletRequest request);

    /**
     * 添加应用
     * @param app 应用
     * @return 应用id
     */
    Long addApp(App app, HttpServletRequest request);

    void delete(DeleteRequest deleteRequest, HttpServletRequest request);

    void edit(App app, AppEditRequest appEditRequest, HttpServletRequest request);

    void updateApp(AppUpdateRequest appUpdateRequest);

    void review(ReviewRequest reviewRequest, HttpServletRequest request);
}
