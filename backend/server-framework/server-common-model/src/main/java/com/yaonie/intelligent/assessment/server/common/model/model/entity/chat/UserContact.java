package com.yaonie.intelligent.assessment.server.common.model.model.entity.chat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 联系人关系表
 */
@Getter
@Setter
public class UserContact implements Serializable {


    /**
     * 用户ID
     */
    @TableId("user_id")
    private String userId;

    /**
     * 其他用户ID 或 群组ID
     */
    @TableField("contact_id")
    private Long contactId;

    /**
     * 联系人类型 (0 : 好友 1 : 群组)
     */
    @TableField("contact_type")
    private Integer contactType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("create_time")
    private Date createTime;

    /**
     * 状态 0:非好友 1:好友 2:已删除好友 3:被好友删除 4:已拉黑好友 5:被好友拉黑
     */
    private Integer status;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String avatar;

    @TableField(exist = false)
    private Date lastMessage;


    @Serial
    private static final long serialVersionUID = 1L;


    @Override
    public String toString() {
        return "UserContact{" +
                "userId=" + userId +
                ", contactId=" + contactId +
                ", contactType=" + contactType +
                ", createTime=" + createTime +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", lastMessage=" + lastMessage +
                '}';
    }
}
