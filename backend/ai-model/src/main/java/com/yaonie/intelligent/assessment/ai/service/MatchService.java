package com.yaonie.intelligent.assessment.ai.service;


import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusAddDto;
import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusGroupAddDto;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupInfo;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-02-04
 */
public interface MatchService {
    List<UserVO> doMatch();

    void doAdd(MilvusAddDto milvusAddDto);

    public void doAddGroup(MilvusGroupAddDto addDto);

    List<GroupInfo> doMatchByGroup();

    void refresh(MilvusAddDto milvusAddDto);
}
