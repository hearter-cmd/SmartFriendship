package com.yaonie.intelligent.assessment.system.domain.dto;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.yaonie.intelligent.assessment.server.common.deserializer.BoolToCharacterDeserializer;
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
public class RoleAddDto {
    private Long roleId;
    private String roleKey;
    private String roleName;
    private Integer roleSort;
    private String dataScope;
    private Boolean menuCheckStrictly;
    private Boolean deptCheckStrictly;
    private List<Long> permissionIds;
    @JsonDeserialize(using = BoolToCharacterDeserializer.class)
    private Character enable;
    @TableLogic(value = "0", delval = "1")
    private String delFlag;
    private String remark;
}
