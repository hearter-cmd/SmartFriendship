package com.yaonie.intelligent.assessment.server.springbootinit.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.scoringResult.ScoringResultQueryRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.evaluation.ScoringResult;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.ScoringResultVO;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import com.yaonie.intelligent.assessment.server.springbootinit.mapper.ScoringResultMapper;
import com.yaonie.intelligent.assessment.server.springbootinit.service.AppService;
import com.yaonie.intelligent.assessment.server.springbootinit.service.ScoringResultService;
import com.yaonie.intelligent.assessment.server.springbootinit.service.UserService;
import com.yaonie.intelligent.assessment.server.springbootinit.utils.SqlUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 评分结果表服务实现
 *
 */
@Service
@Slf4j
public class ScoringResultServiceImpl extends ServiceImpl<ScoringResultMapper, ScoringResult> implements ScoringResultService {

    @Resource
    private UserService userService;

    @Resource
    private AppService appService;

    /**
     * 校验数据
     *
     * @param scoringResult
     * @param add      对创建的数据进行校验
     */
    @Override
    public void validScoringResult(ScoringResult scoringResult, boolean add) {
        ThrowUtils.throwIf(scoringResult == null, ErrorCode.PARAMS_ERROR);
        // 从对象中取值
        String resultName = scoringResult.getResultName();
        String resultDesc = scoringResult.getResultDesc();
        String resultPicture = scoringResult.getResultPicture();
        String resultProp = scoringResult.getResultProp();
        Integer resultScoreRange = scoringResult.getResultScoreRange();
        Long appId = scoringResult.getAppId();
        Long userId = scoringResult.getUserId();
        // 创建数据时，参数不能为空
        if (add) {
            // 补充校验规则
            ThrowUtils.throwIf(StringUtils.isBlank(resultName), ErrorCode.PARAMS_ERROR, "结果名称不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(resultDesc), ErrorCode.PARAMS_ERROR, "结果描述不能为空");
            ThrowUtils.throwIf(StringUtils.isBlank(resultProp), ErrorCode.PARAMS_ERROR, "结果属性不能为空");
            ThrowUtils.throwIf(resultScoreRange == null, ErrorCode.PARAMS_ERROR, "结果分数范围不能为空");
            ThrowUtils.throwIf(appId == null, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        }
        // 修改数据时，有参数则校验
        // 补充校验规则
        if (StringUtils.isNotBlank(resultName)) {
            ThrowUtils.throwIf(resultName.length() > 128, ErrorCode.PARAMS_ERROR, "结果名称过长");
        }
        if (StringUtils.isNotBlank(resultPicture)) {
            ThrowUtils.throwIf(resultPicture.length() > 1024, ErrorCode.PARAMS_ERROR, "结果图片过长");
        }
        if (StringUtils.isNotBlank(resultDesc)) {
            ThrowUtils.throwIf(resultDesc.length() > 1024, ErrorCode.PARAMS_ERROR, "结果描述过长");
        }
        if (StringUtils.isNotBlank(resultProp)) {
            ThrowUtils.throwIf(resultProp.length() > 128, ErrorCode.PARAMS_ERROR, "结果属性过长");
        }
        if (resultScoreRange != null) {
            ThrowUtils.throwIf(resultScoreRange < 0, ErrorCode.PARAMS_ERROR, "结果分数范围不能小于0");
        }
        if (appId != null) {
            ThrowUtils.throwIf(appService.getById(appId) == null, ErrorCode.PARAMS_ERROR, "应用ID不存在");
        }
        if (userId != null) {
            ThrowUtils.throwIf(userService.getById(userId) == null, ErrorCode.PARAMS_ERROR, "用户ID不存在");
        }
    }

    /**
     * 获取查询条件
     *
     * @param scoringResultQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<ScoringResult> getQueryWrapper(ScoringResultQueryRequest scoringResultQueryRequest) {
        QueryWrapper<ScoringResult> queryWrapper = new QueryWrapper<>();
        if (scoringResultQueryRequest == null) {
            return queryWrapper;
        }
        Long id = scoringResultQueryRequest.getId();
        String resultName = scoringResultQueryRequest.getResultName();
        String resultDesc = scoringResultQueryRequest.getResultDesc();
        String resultPicture = scoringResultQueryRequest.getResultPicture();
        List<String> resultProp = scoringResultQueryRequest.getResultProp();
        Long appId = scoringResultQueryRequest.getAppId();
        Long userId = scoringResultQueryRequest.getUserId();
        Date createTime = scoringResultQueryRequest.getCreateTime();
        Long notId = scoringResultQueryRequest.getNotId();
        String searchText = scoringResultQueryRequest.getSearchText();
        String sortField = scoringResultQueryRequest.getSortField();
        String sortOrder = scoringResultQueryRequest.getSortOrder();
        // todo 补充需要的查询条件
        // 从多字段中搜索
        if (StringUtils.isNotBlank(searchText)) {
            // 需要拼接查询条件
            queryWrapper.and(qw -> qw.like("resultName", searchText).or().like("resultDesc", searchText));
        }
        // 模糊查询
        queryWrapper.like(StringUtils.isNotBlank(resultName), "resultName", resultName);
        queryWrapper.like(StringUtils.isNotBlank(resultDesc), "resultDesc", resultDesc);
        // 精确查询
        queryWrapper.ne(ObjectUtils.isNotEmpty(notId), "id", notId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(resultPicture), "resultPicture", resultPicture);
        queryWrapper.eq(ObjectUtils.isNotEmpty(resultProp), "resultProp", JSONUtil.toJsonStr(resultProp));
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(appId), "appId", appId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(createTime), "createTime", createTime);
        // 排序规则
        queryWrapper.orderBy(SqlUtils.validSortField(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    /**
     * 获取评分结果表封装
     *
     * @param scoringResult
     * @param request
     * @return
     */
    @Override
    public ScoringResultVO getScoringResultVO(ScoringResult scoringResult, HttpServletRequest request) {
        // 对象转封装类
        ScoringResultVO scoringResultVO = ScoringResultVO.objToVo(scoringResult);

        // 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Long userId = scoringResult.getUserId();
        User user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVO userVO = userService.getUserVO(user);
        scoringResultVO.setUser(userVO);
        // endregion

        return scoringResultVO;
    }

    /**
     * 分页获取评分结果表封装
     *
     * @param scoringResultPage
     * @param request
     * @return
     */
    @Override
    public Page<ScoringResultVO> getScoringResultVOPage(Page<ScoringResult> scoringResultPage, HttpServletRequest request) {
        List<ScoringResult> scoringResultList = scoringResultPage.getRecords();
        Page<ScoringResultVO> scoringResultVOPage = new Page<>(scoringResultPage.getCurrent(), scoringResultPage.getSize(), scoringResultPage.getTotal());
        if (CollUtil.isEmpty(scoringResultList)) {
            return scoringResultVOPage;
        }
        // 对象列表 => 封装对象列表
        List<ScoringResultVO> scoringResultVOList = scoringResultList.stream().map(scoringResult -> ScoringResultVO.objToVo(scoringResult)).collect(Collectors.toList());

        // todo 可以根据需要为封装对象补充值，不需要的内容可以删除
        // region 可选
        // 1. 关联查询用户信息
        Set<Long> userIdSet = scoringResultList.stream().map(ScoringResult::getUserId).collect(Collectors.toSet());
        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));
        // 填充信息
        scoringResultVOList.forEach(scoringResultVO -> {
            Long userId = scoringResultVO.getUserId();
            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            scoringResultVO.setUser(userService.getUserVO(user));
        });
        // endregion

        scoringResultVOPage.setRecords(scoringResultVOList);
        return scoringResultVOPage;
    }

}
