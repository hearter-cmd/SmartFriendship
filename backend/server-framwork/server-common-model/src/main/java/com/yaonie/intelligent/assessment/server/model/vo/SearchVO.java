package com.yaonie.intelligent.assessment.server.model.vo;


import lombok.Data;

import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-05 18:04
 * @Author 武春利
 * @CreateTime 2024-09-05
 * @ClassName SearchVo
 * @Project backend
 * @Description : TODO
 */
@Data
public class SearchVO {

    List<QuestionVO> questionVOList;

    List<UserVO> userVOList;

    List<AppVO> appVOList;

}
