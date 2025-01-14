package com.yaonie.intelligent.assessment.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yaonie.intelligent.assessment.system.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;

/**
 * 字典类型表 sys_dict_type
 * 
 * @author ruoyi
 */
@Schema(name = "字典类型表")
@Getter
@Setter
@TableName(value = "sys_dict_type")
public class SysDictType extends BaseEntity
{
    @Serial
    private static final long serialVersionUID = 1L;

    /** 字典主键 */
    @SchemaProperty(name = "字典主键")
    @TableId(value = "dict_id", type = IdType.ASSIGN_ID)
    private Long dictId;

    /** 字典名称 */
    @SchemaProperty(name = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    @Size(min = 0, max = 100, message = "字典类型名称长度不能超过100个字符")
    @TableField(value = "dict_name")
    private String dictName;

    /** 字典类型 */
    @SchemaProperty(name = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型类型长度不能超过100个字符")
    @Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）")
    @TableField(value = "dict_type")
    private String dictType;

    /** 状态（0正常 1停用） */
    @SchemaProperty(name = "状态")
    @TableField(value = "status")
    private String status;
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dictId", getDictId())
            .append("dictName", getDictName())
            .append("dictType", getDictType())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
