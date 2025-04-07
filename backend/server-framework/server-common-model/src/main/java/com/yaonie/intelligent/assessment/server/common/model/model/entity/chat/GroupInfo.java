package com.yaonie.intelligent.assessment.server.common.model.model.entity.chat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 群聊存档
 *
 * @author 77160
 */
@Data
@Schema(name = "群聊存档")
@TableName("group_info")
public class GroupInfo implements Serializable {
    /**
     * 群聊ID
     */
    @SchemaProperty(name = "群聊ID")
    @TableId(value = "group_id", type = IdType.ASSIGN_ID)
    private Long groupId;

    /**
     * 群组名
     */
    @SchemaProperty(name = "群组名")
    @TableField(value = "group_name")
    private String groupName;

    /**
     * 群头像链接
     */
    @SchemaProperty(name = "群头像链接")
    @TableField(value = "group_avatar")
    private String groupAvatar;

    /**
     * 群主ID
     */
    @SchemaProperty(name = "群主ID")
    @TableField(value = "group_owner_id")
    private Long groupOwnerId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @SchemaProperty(name = "创建时间")
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 群公告
     */
    @SchemaProperty(name = "群公告")
    @TableField(value = "group_notice")
    private String groupNotice;

    /**
     * 群公告
     */
    @SchemaProperty(name = "群标签")
    @TableField(value = "tags")
    private String tags;

    /**
     * 加入方式 (0 : 直接加入, 1 : 管理员同意后加入)
     */
    @SchemaProperty(name = "加入方式 (0 : 直接加入, 1 : 管理员同意后加入)")
    @TableField(value = "join_type")
    private Integer joinType;

    /**
     * 状态 (1 : 正常 0 : 解散)
     */
    @SchemaProperty(name = "状态 (1 : 正常 0 : 解散)")
    @TableLogic(value = "status", delval = "0")
    private Integer status;


    @Override
    public String toString() {
        return "GroupInfo{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", groupAvatar='" + groupAvatar + '\'' +
                ", groupOwnerId=" + groupOwnerId +
                ", createTime=" + createTime +
                ", groupNotice='" + groupNotice + '\'' +
                ", joinType=" + joinType +
                ", status=" + status +
                '}';
    }
}
