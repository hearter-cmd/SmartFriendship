package com.yaonie.intelligent.assessment.server.springbootinit.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.common.DeleteRequest;
import com.yaonie.intelligent.assessment.server.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.ReviewRequest;
import com.yaonie.intelligent.assessment.server.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.model.dto.app.AppEditRequest;
import com.yaonie.intelligent.assessment.server.model.dto.app.AppQueryRequest;
import com.yaonie.intelligent.assessment.server.model.dto.app.AppUpdateRequest;
import com.yaonie.intelligent.assessment.server.model.entity.evaluation.App;
import com.yaonie.intelligent.assessment.server.model.entity.User;
import com.yaonie.intelligent.assessment.server.model.enums.AppScoringStrategyEnum;
import com.yaonie.intelligent.assessment.server.model.enums.AppTypeEnum;
import com.yaonie.intelligent.assessment.server.model.enums.ReviewStatusEnum;
import com.yaonie.intelligent.assessment.server.model.vo.AppVO;
import com.yaonie.intelligent.assessment.server.model.vo.UserVO;
import com.yaonie.intelligent.assessment.server.springbootinit.mapper.AppMapper;
import com.yaonie.intelligent.assessment.server.springbootinit.service.AppService;
import com.yaonie.intelligent.assessment.server.springbootinit.service.UserService;
import com.yaonie.intelligent.assessment.server.springbootinit.utils.SqlUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 应用服务实现
 *
 * @author 77160
 */
@Service
@Slf4j
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Resource
    private UserService userService;



    /**
     * 校验数据
     *
     * @param app     应用
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validApp(App app, boolean add) {
        ThrowUtils.throwIf(app == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String appName = app.getAppName();
        String appDesc = app.getAppDesc();
        String appIcon = app.getAppIcon();
        Integer appType = app.getAppType();
        Integer scoringStrategy = app.getScoringStrategy();
        Integer reviewStatus = app.getReviewStatus();
        String reviewMessage = app.getReviewMessage();
        Long reviewerId = app.getReviewerId();
        Date reviewTime = app.getReviewTime();
        Long userId = app.getUserId();
        // todo 补充需要的校验规则

        // 创建数据时，参数不能为空
        if (add) {
            // todo 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(appName), ErrorCode.PARAMS_ERROR, "应用名称不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(appDesc), ErrorCode.PARAMS_ERROR, "应用描述不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(appIcon), ErrorCode.PARAMS_ERROR, "应用图标非法");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(appType), ErrorCode.PARAMS_ERROR, "应用类型非法");
            ThrowUtils.throwIf(ObjectUtils.isEmpty(scoringStrategy), ErrorCode.PARAMS_ERROR, "评分策略非法");
        }
        // 修改数据时，有参数则校验
        // todo 补充校验规则
        if (StringUtils.isNotBlank(appName)) {
            ThrowUtils.throwIf(appName.length() > 128, ErrorCode.PARAMS_ERROR, "应用名称过长");
        }
        if (StringUtils.isNotBlank(appDesc)) {
            ThrowUtils.throwIf(appDesc.length() > 2048, ErrorCode.PARAMS_ERROR, "应用描述过长");
        }
        if (StringUtils.isNotBlank(appIcon)) {
            ThrowUtils.throwIf(appIcon.length() > 1024, ErrorCode.PARAMS_ERROR, "应用图标过长");
        }
        if (ObjectUtils.isNotEmpty(appType)) {
            ThrowUtils.throwIf(AppTypeEnum.getEnumByValue(appType) == null, ErrorCode.PARAMS_ERROR, "应用类型不合法");
        }
        if (ObjectUtils.isNotEmpty(scoringStrategy)) {
            ThrowUtils.throwIf(AppScoringStrategyEnum.getEnumByValue(scoringStrategy) == null, ErrorCode.PARAMS_ERROR, "评分策略不合法");
        }
        if (ObjectUtils.isNotEmpty(reviewStatus)) {
            ThrowUtils.throwIf(ReviewStatusEnum.getEnumByValue(reviewStatus) == null, ErrorCode.PARAMS_ERROR, "审核状态不合法");
        }
        if (StringUtils.isNotBlank(reviewMessage)) {
            ThrowUtils.throwIf(reviewMessage.length() > 2048, ErrorCode.PARAMS_ERROR, "审核信息过长");
        }
        if (ObjectUtils.isNotEmpty(reviewTime)) {
            ThrowUtils.throwIf(reviewTime.after(new Date()), ErrorCode.PARAMS_ERROR, "审核时间不合法");
        }
        if (ObjectUtils.isNotEmpty(reviewerId)) {
            User user = userService.getById(reviewerId);
            ThrowUtils.throwIf(user == null, ErrorCode.PARAMS_ERROR, "审核人不存在");
        }
        if (ObjectUtils.isNotEmpty(userId)) {
            User user = userService.getById(userId);
            ThrowUtils.throwIf(user == null, ErrorCode.PARAMS_ERROR, "用户不存在");
        }
    }

    /**
     * 获取查询条件
     *
     * @param appQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<App> getQueryWrapper(AppQueryRequest appQueryRequest) {
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        if (appQueryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = appQueryRequest.getId();
        String appName = appQueryRequest.getAppName();
        String appDesc = appQueryRequest.getAppDesc();
        String appIcon = appQueryRequest.getAppIcon();
        Integer appType = appQueryRequest.getAppType();
        Integer scoringStrategy = appQueryRequest.getScoringStrategy();
        Integer reviewStatus = appQueryRequest.getReviewStatus();
        String reviewMessage = appQueryRequest.getReviewMessage();
        Long reviewerId = appQueryRequest.getReviewerId();
        Long userId = appQueryRequest.getUserId();
        Integer isDelete = appQueryRequest.getIsDelete();
        Long notId = appQueryRequest.getNotId();
        String searchText = appQueryRequest.getSearchText();
        int current = appQueryRequest.getCurrent();
        int pageSize = appQueryRequest.getPageSize();
        String sortField = appQueryRequest.getSortField();
        String sortOrder = appQueryRequest.getSortOrder();
        // todo 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw ->
                    qw
                            .like("appName", searchText)
                            .or()
                            .like("appDesc", searchText)
            );
        }
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(appName), "appName", appName);
        queryWrapper.like(StringUtils.isNotBlank(appDesc), "appDesc", appDesc);
        queryWrapper.like(StringUtils.isNotBlank(reviewMessage), "reviewMessage", reviewMessage);
        // 精确查询
        queryWrapper.eq(ObjectUtils.isNotEmpty(appIcon), "appIcon", appIcon);
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(appType), "appType", appType);
        queryWrapper.eq(ObjectUtils.isNotEmpty(scoringStrategy), "scoringStrategy", scoringStrategy);
        queryWrapper.eq(ObjectUtils.isNotEmpty(reviewStatus), "reviewStatus", reviewStatus);
        queryWrapper.eq(ObjectUtils.isNotEmpty(reviewerId), "reviewerId", reviewerId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(isDelete), "isDelete", isDelete);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取应用封装
     *
     * @param app
     * @param request
     * @return
     */
    @Override
    public AppVO getAppVO(App app, HttpServletRequest request) {
        // 对象转封装类
        AppVO appVO = AppVO.objToVo(app);

        // 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Long userId = app.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        appVO.setUser(userVO);
        // endregion

        return appVO;
    }

    /**
     * 分页获取应用封装
     *
     * @param appPage
     * @param request
     * @return
     */
    @Override
    public Page<AppVO> getAppVOPage(Page<App> appPage, HttpServletRequest request) {
        List<App> appList = appPage.getRecords();
        Page<AppVO> appVOPage = new Page<>(appPage.getCurrent(), appPage.getSize(), appPage.getTotal());
        if (CollUtil.isEmpty(appList)) {
            return appVOPage;
        }
        // 对象列表 => 封装对象列表
        List<AppVO> appVOList = appList.stream().map(AppVO::objToVo).collect(Collectors.toList());

        // 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = appList.stream().map(App::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        appVOList.forEach(appVO -> {
            Long userId = appVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            appVO.setUser(userService.getUserVO(user));
        });
        // endregion

        appVOPage.setRecords(appVOList);
        return appVOPage;
    }

    @Override
    public Long addApp(App app, HttpServletRequest request) {
        // 数据校验
        validApp(app, true);
        // todo 填充默认值
        User loginUser = userService.getLoginUser(request);
        app.setUserId(loginUser.getId());
        // 写入数据库
        boolean result = save(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        // 返回新写入的数据 id
        long newAppId = app.getId();
        return newAppId;
    }

    @Override
    public void delete(DeleteRequest deleteRequest, HttpServletRequest request) {
        long deleteId = deleteRequest.getId();
        long id = isAdminOrMe(deleteId, request);
        // 操作数据库
        boolean result = removeById(id);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
    }

    @Override
    public void edit(App app, AppEditRequest appEditRequest, HttpServletRequest request) {
        // 数据校验
        validApp(app, false);
        long editId = appEditRequest.getId();
        isAdminOrMe(editId, request);
        // 操作数据库
        boolean result = updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
    }

    @Override
    public void updateApp(AppUpdateRequest appUpdateRequest) {
        // todo 在此处将实体类和 DTO 进行转换
        App app = new App();
        BeanUtils.copyProperties(appUpdateRequest, app);
        // 数据校验
        validApp(app, false);
        // 判断是否存在
        long id = appUpdateRequest.getId();
        App oldApp = getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 操作数据库
        boolean result = updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
    }

    @Override
    public void review(ReviewRequest reviewRequest, HttpServletRequest request) {
        // 在此处将实体类和 DTO 进行转换
        App app = new App();
        BeanUtils.copyProperties(reviewRequest, app);
        // 数据校验
        User loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        // 判断是否存在
        long id = reviewRequest.getId();
        App oldApp = getById(id);
        if (oldApp.getReviewStatus().equals(reviewRequest.getReviewStatus())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "审核状态未改变");
        }
        app.setReviewerId(userId);
        app.setReviewTime(new Date());
        // 操作数据库, 进行更新
        boolean result = updateById(app);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
    }

    private long isAdminOrMe(Long id, HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        // 判断是否存在
        App oldApp = getById(id);
        ThrowUtils.throwIf(oldApp == null, ErrorCode.NOT_FOUND_ERROR);
        // 仅本人或管理员可删除
        if (!oldApp.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return id;
    }

}
