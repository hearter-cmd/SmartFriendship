package com.yaonie.intelligent.assessment.ai.service;


import com.yaonie.intelligent.assessment.ai.domain.model.dto.match.MilvusAddDto;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;

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
    List<User> doMatch();

    void doAdd(MilvusAddDto milvusAddDto);
}
