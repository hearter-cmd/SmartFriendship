package com.yaonie.intelligent.assessment.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yaonie.intelligent.assessment.server.common.deserializer.BoolToCharacterDeserializer;
import com.yaonie.intelligent.assessment.server.common.serializer.LongToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜单权限表
 *
 * @author 武春利
 * @TableName sys_menu
 */
@Schema(title = "菜单权限表")
@TableName(value = "sys_menu")
@Getter
@Setter
@ToString(callSuper = true, includeFieldNames = true)
@JsonInclude()
public class SysMenu implements Serializable {
    /**
     * 菜单ID
     */
    @SchemaProperty(name = "菜单ID")
    @JsonSerialize(using = LongToStringSerializer.class)
    @JsonProperty(value = "id", index = 0)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @SchemaProperty(name = "菜单名称")
    @TableField(value = "name")
    @JsonProperty(value = "name", index = 1)
    private String name;

    // 菜单编码区分是否选中的样式

    @SchemaProperty(name = "菜单编码")
    @TableField(value = "code")
    @JsonProperty(value = "code", index = 2)
    private String code;

    @SchemaProperty(name = "菜单类型")
    @TableField(value = "type")
    @JsonProperty(value = "type", index = 3)
    private String type;

    @JsonProperty(value = "parentId", index = 4)
    @SchemaProperty(name = "父菜单ID")
    @TableField(value = "parentId")
    private Long parentId;

    /**
     * 路由地址
     */
    @JsonProperty(value = "path", index = 5)
    @SchemaProperty(name = "路由地址")
    @TableField(value = "path")
    private String path;

    @JsonProperty(value = "redirect", index = 6)
    @SchemaProperty(name = "重定向")
    @TableField(value = "redirect")
    private String redirect;

    /**
     * 菜单图标
     */
    @JsonProperty(value = "icon", index = 7)
    @SchemaProperty(name = "菜单图标")
    @TableField(value = "icon")
    private String icon;

    /**
     * 组件路径
     */
    @JsonProperty(value = "component", index = 8)
    @SchemaProperty(name = "组件路径")
    @TableField(value = "component")
    private String component;

    @JsonProperty(value = "layout", index = 9)
    @SchemaProperty(name = "布局")
    @TableField(value = "layout")
    private String layout;

    /**
     * 是否为外链（0是 1否）
     */
    @SchemaProperty(name = "是否为外链")
    @TableField(value = "isFrame")
    private Integer isFrame;

    /**
     * 是否缓存（0不缓存 1缓存）
     */
    @JsonProperty(value = "keepAlive", index = 10)
    @SchemaProperty(name = "是否缓存")
    @TableField(value = "isCache")
    @JsonDeserialize(using = BoolToCharacterDeserializer.class)
    @JsonSerialize(using = CharacterToBooleanSerializer.class)
    private Character isCache;

//    @JsonProperty("method")
//    @SchemaProperty(name = "请求方法")
//    @TableField(value = "method")
//    private String method;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @JsonProperty(value = "show", index = 11)
    @SchemaProperty(name = "是否显示 (0:N, 1:Y)")
    @TableField(value = "visible")
    @JsonDeserialize(using = BoolToCharacterDeserializer.class)
    @JsonSerialize(using = CharacterToBooleanSerializer.class)
    private Character visible;

    /**
     * 备注
     */
    @JsonProperty(value = "description", index = 12)
    @SchemaProperty(name = "备注")
    @TableField(value = "remark")
    private String remark;

    /**
     * 菜单状态（0正常 1停用）
     */
    @JsonProperty(value = "enable", index = 13)
    @SchemaProperty(name = "菜单状态 (0:N, 1:Y)")
    @TableField(value = "enable")
    @JsonDeserialize(using = BoolToCharacterDeserializer.class)
    @JsonSerialize(using = CharacterToBooleanSerializer.class)
    private Character enable;

    @JsonProperty(value = "order", index = 14)
    @SchemaProperty(name = "显示顺序")
    @TableField(value = "orderNum")
    private Integer orderNum;

    /**
     * 权限标识
     */
    @JsonIgnore
    @SchemaProperty(name = "权限标识")
    @TableField(value = "perms")
    private String perms;

    /**
     * 创建者
     */
    @JsonIgnore
    @SchemaProperty(name = "创建者")
    @TableField(value = "createBy")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonIgnore
    @SchemaProperty(name = "创建时间")
    @TableField(value = "createTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @JsonIgnore
    @SchemaProperty(name = "更新者")
    @TableField(value = "updateBy")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonIgnore
    @SchemaProperty(name = "更新时间")
    @TableField(value = "updateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 是否删除（0未删 1已删）
     */
    @SchemaProperty(name = "是否删除")
    @TableLogic(value = "0", delval = "1")
    private Short isDeleted;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private List<SysMenu> children;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


    private static class CharacterToBooleanSerializer extends JsonSerializer<Character> {
        @Override
        public void serialize(Character character, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeBoolean(character.equals('1'));
        }
    }
}