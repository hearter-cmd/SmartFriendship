package com.yaonie.intelligent.assessment.server.common.model.model.dto.app;

import com.yaonie.intelligent.assessment.server.common.model.model.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * 查询应用请求
 *
 * @author 77160
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AppQueryRequest extends PageRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用描述
     */
    private String appDesc;

    /**
     * 应用图标
     */
    private String appIcon;

    /**
     * 应用类型（0-得分类，1-测评类）
     */
    private Integer appType;

    /**
     * 评分策略（0-自定义，1-AI）
     */
    private Integer scoringStrategy;

    /**
     * 审核状态：0-待审核, 1-通过, 2-拒绝
     */
    private Integer reviewStatus;

    /**
     * 审核信息
     */
    private String reviewMessage;

    /**
     * 审核人 id
     */
    private Long reviewerId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 不查id
     */
    private Long notId;

    /**
     * 搜索关键字
     */
    private String searchText;

    @Serial
    private static final long serialVersionUID = 1L;
}