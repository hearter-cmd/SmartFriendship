package com.yaonie.intelligent.assessment.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant;
import com.yaonie.intelligent.assessment.system.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serial;
import java.io.Serializable;

/**
 * 字典数据表 sys_dict_data
 *
 * @author 武春利
 */
@Getter
@Setter
@Schema(name = "字典数据", description = "字典数据表")
@TableName(value = "sys_dict_data")
public class SysDictData extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典编码
     */
    @SchemaProperty(name = "字典编码")
    private Long dictCode;

    /**
     * 字典排序
     */
    @SchemaProperty(name = "字典排序")
    private Long dictSort;

    /**
     * 字典标签
     */
    @SchemaProperty(name = "字典标签")
    @NotBlank(message = "字典标签不能为空")
    @Size(min = 0, max = 100, message = "字典标签长度不能超过100个字符")
    private String dictLabel;

    /**
     * 字典键值
     */
    @SchemaProperty(name = "字典键值")
    @NotBlank(message = "字典键值不能为空")
    @Size(min = 0, max = 100, message = "字典键值长度不能超过100个字符")
    private String dictValue;

    /**
     * 字典类型
     */
    @SchemaProperty(name = "字典类型")
    @NotBlank(message = "字典类型不能为空")
    @Size(min = 0, max = 100, message = "字典类型长度不能超过100个字符")
    private String dictType;

    /**
     * 样式属性（其他样式扩展）
     */
    @SchemaProperty(name = "样式属性")
    @Size(min = 0, max = 100, message = "样式属性长度不能超过100个字符")
    private String cssClass;

    /**
     * 表格字典样式
     */
    @SchemaProperty(name = "表格字典样式")
    private String listClass;

    /**
     * 是否默认（Y是 N否）
     */
    @SchemaProperty(name = "是否默认")
    private String isDefault;

    /**
     * 状态（0正常 1停用）
     */
    @SchemaProperty(name = "状态")
    private String status;


    public boolean getDefault() {
        return UserConstant.YES.equals(this.isDefault);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dictCode", getDictCode())
                .append("dictSort", getDictSort())
                .append("dictLabel", getDictLabel())
                .append("dictValue", getDictValue())
                .append("dictType", getDictType())
                .append("cssClass", getCssClass())
                .append("listClass", getListClass())
                .append("isDefault", getIsDefault())
                .append("status", getStatus())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}