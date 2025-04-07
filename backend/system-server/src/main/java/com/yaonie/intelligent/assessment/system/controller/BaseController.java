package com.yaonie.intelligent.assessment.system.controller;

import com.yaonie.intelligent.assessment.server.common.constant.HttpStatus;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.sql.SqlUtil;
import com.yaonie.intelligent.assessment.server.common.util.DateUtils;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.server.common.util.StringUtils;
import com.yaonie.intelligent.assessment.system.domain.page.PageDomain;
import com.yaonie.intelligent.assessment.system.domain.page.TableDataInfo;
import com.yaonie.intelligent.assessment.system.domain.page.TableSupport;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * web层通用数据处理
 *
 * @author ruoyi
 */
public class BaseController {

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求排序数据
     */
    protected void startOrderBy() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy())) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(list);
        return rspData;
    }

    /**
     * 返回成功
     */
    public BaseResponse<?> success() {
        return ResultUtils.success(null);
    }

    /**
     * 返回失败消息
     */
    public BaseResponse<?> error() {
        return ResultUtils.error(null);
    }

    /**
     * 返回成功消息
     */
    public BaseResponse<?> success(String message) {
        return ResultUtils.success(message);
    }

    /**
     * 返回成功消息
     */
    public BaseResponse<?> success(Object data) {
        return ResultUtils.success(data);
    }

    /**
     * 返回失败消息
     */
    public BaseResponse<?> error(String message) {
        return ResultUtils.error(ErrorCode.OPERATION_ERROR, message);
    }

    /**
     * 返回警告消息
     */
    public BaseResponse<?> warn(String message) {
        return ResultUtils.success(message);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected BaseResponse<?> toAjax(int rows) {
        return rows > 0 ? ResultUtils.success(null) : ResultUtils.error(null);
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected BaseResponse<?> toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * 页面跳转
     */
    public String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * 获取用户缓存信息
     */
    public User getLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    /**
     * 获取登录用户名
     */
    public String getUsername() {
        return SecurityUtils.getUsername();
    }
}
