package com.yaonie.intelligent.assessment.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yaonie.intelligent.assessment.server.common.serializer.CharacterToBoolSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author 77160
 * @since 2025年1月9日
 */
@Data
@Schema(description = "角色表")
@TableName("sys_role")
public class SysRole {

    @TableId(value = "roleId", type = IdType.ASSIGN_ID)
    private String roleId;

    private String roleKey;

    private String roleName;

    @JsonSerialize(using = CharacterToBoolSerializer.class)
    @TableField(value = "enable")
    private Character enable;

    private Integer roleSort;

    private String dataScope;

    private Boolean menuCheckStrictly;

    private Boolean deptCheckStrictly;

    @TableLogic(value = "0", delval = "1")
    private String isDelete;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @Schema(description = "更新人")
    private String updateBy;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
    private String remark;
} 