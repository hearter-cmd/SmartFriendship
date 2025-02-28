package com.yaonie.intelligent.assessment.server.chat_server.common.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 聊天记录表
 * @author 77160
 */
@Data
@NoArgsConstructor
@TableName("message")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Message implements Serializable {


	/**
	 * 唯一标识
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 群聊/好友 (发送目标) id
	 */
	@SchemaProperty(name = "群聊/好友 (发送目标) id")
	@TableField(value = "contactId")
	private Long contactId;

	/**
	 * 用户Id
	 */
	@SchemaProperty(name = "用户Id")
	@TableField(value = "userId")
	private Long userId;

	/**
	 * 消息主体 (空消息不允许发送)
	 */
	@SchemaProperty(name = "消息主体 (空消息不允许发送)")
	@TableField(value = "message")
	private String message;

	/**
	 * 目标类型 (群组 , 好友)
	 */
	@SchemaProperty(name = "目标类型 (群组 , 好友)")
	@TableField(value = "type")
	private Integer type;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;

	/**
	 * 发送者昵称
	 */
	@SchemaProperty(name = "发送者昵称")
	@TableField(value = "userName")
	private String userName;

	/**
	 * 发送者头像
	 */
	@SchemaProperty(name = "发送者头像")
	@TableField(value = "avatar")
	private String avatar;

	/**
	 * 是否删除 (0:未删除 1:删除)
	 */
	@TableLogic(value = "0", delval = "1")
	private Integer isDelete;

	@Serial
	private static final long serialVersionUID = 1L;
}
