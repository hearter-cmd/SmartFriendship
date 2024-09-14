package com.yaonie.intelligent.assessment.server.model.vo;

import cn.hutool.json.JSONUtil;
import com.yaonie.intelligent.assessment.server.model.entity.evaluation.ScoringResult;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 评分结果表视图
 *
 * @author 武春利
 */
@Data
public class ScoringResultVO implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 结果名称，如物流师
     */
    private String resultName;

    /**
     * 结果描述
     */
    private String resultDesc;

    /**
     * 结果图片
     */
    private String resultPicture;

    /**
     * 结果属性集合 JSON，如 [I,S,T,J]
     */
    private List<String> resultProp;

    /**
     * 结果得分范围，如 80，表示 80及以上的分数命中此结果
     */
    private Integer resultScoreRange;

    /**
     * 应用 id
     */
    private Long appId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户信息
     */
    private UserVO user;

    /**
     * 封装类转对象
     *
     * @param scoringResultVO 评分结果视图
     * @return ScoringResult 评分结果
     */
    public static ScoringResult voToObj(ScoringResultVO scoringResultVO) {
        if (scoringResultVO == null) {
            return null;
        }
        ScoringResult scoringResult = new ScoringResult();
        BeanUtils.copyProperties(scoringResultVO, scoringResult);
        List<String> resultProp = scoringResultVO.getResultProp();
        scoringResult.setResultProp(JSONUtil.toJsonStr(resultProp));
        return scoringResult;
    }

    /**
     * 对象转封装类
     *
     * @param scoringResult 评分结果
     * @return ScoringResultVO 评分结果视图
     */
    public static ScoringResultVO objToVo(ScoringResult scoringResult) {
        if (scoringResult == null) {
            return null;
        }
        ScoringResultVO scoringResultVO = new ScoringResultVO();
        BeanUtils.copyProperties(scoringResult, scoringResultVO);
        String resultProp = scoringResult.getResultProp();
        scoringResultVO.setResultProp(JSONUtil.toList(resultProp, String.class));
        return scoringResultVO;
    }
}
