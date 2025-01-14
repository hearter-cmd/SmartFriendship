package com.yaonie.intelligent.assessment.system.util;

import com.yaonie.intelligent.assessment.server.common.sql.SqlUtil;
import com.yaonie.intelligent.assessment.system.domain.page.PageDomain;
import com.yaonie.intelligent.assessment.system.domain.page.TableSupport;

/**
 * 分页工具类
 * 
 * @author ruoyi
 */
public class PageUtils {
    /**
     * 设置请求分页数据
     */
//    public static void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNo();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
    }

}
