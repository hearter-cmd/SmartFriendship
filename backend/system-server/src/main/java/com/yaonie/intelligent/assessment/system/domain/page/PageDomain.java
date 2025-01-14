package com.yaonie.intelligent.assessment.system.domain.page;

import com.yaonie.intelligent.assessment.server.common.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页数据
 * 
 * @author ruoyi
 */
public class PageDomain
{
    /** 当前记录起始索引 */
    @Setter
    @Getter
    private Integer pageNo;

    /** 每页显示记录数 */
    @Setter
    @Getter
    private Integer pageSize;

    /** 排序列 */
    @Setter
    @Getter
    private String orderByColumn;

    /** 排序的方向desc或者asc */
    @Getter
    private String isAsc = "asc";

    /** 分页参数合理化 */
    @Setter
    private Boolean reasonable = true;

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

    public void setIsAsc(String isAsc)
    {
        if (StringUtils.isNotEmpty(isAsc))
        {
            // 兼容前端排序类型
            if ("ascending".equals(isAsc))
            {
                isAsc = "asc";
            }
            else if ("descending".equals(isAsc))
            {
                isAsc = "desc";
            }
            this.isAsc = isAsc;
        }
    }

    public Boolean getReasonable()
    {
        if (StringUtils.isNull(reasonable))
        {
            return Boolean.TRUE;
        }
        return reasonable;
    }

}
