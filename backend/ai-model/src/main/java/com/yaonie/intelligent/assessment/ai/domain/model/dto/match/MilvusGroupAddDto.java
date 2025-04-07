package com.yaonie.intelligent.assessment.ai.domain.model.dto.match;


import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-04-05
 */
@Data
public class MilvusGroupAddDto {
    private String groupId;
    private List<String> tags;
}
