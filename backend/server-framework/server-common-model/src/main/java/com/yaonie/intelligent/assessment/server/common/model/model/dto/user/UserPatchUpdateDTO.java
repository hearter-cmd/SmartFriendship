package com.yaonie.intelligent.assessment.server.common.model.model.dto.user;


import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-13
 */
@Data
public class UserPatchUpdateDTO {
    private List<String> roleIds;
}
