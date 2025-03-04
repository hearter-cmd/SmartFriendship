package com.yaonie.intelligent.assessment.server.chat_server.user.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.DateTimePatternEnum;
import com.yaonie.intelligent.assessment.server.chat_server.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 群聊存档
 * @author 77160
 */
@Data
@Schema(name = "群聊存档")
public class GroupInfo implements Serializable {


	/**
	 * 群聊ID
	 */
	@SchemaProperty(name = "群聊ID")
	@TableId("group_id")
	private Long groupId;

	/**
	 * 群组名
	 */
	@SchemaProperty(name = "群组名")
	@TableField("group_name")
	private String groupName;

	/**
	 * 群头像链接
	 */
	@SchemaProperty(name = "群头像链接")
	@TableField("group_avatar")
	private String groupAvatar;

	/**
	 * 群主ID
	 */
	@SchemaProperty(name = "群主ID")
	@TableField("group_owner_id")
	private Long groupOwnerId;

	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@SchemaProperty(name = "创建时间")
	@TableField("create_time")
	private Date createTime;

	/**
	 * 群公告
	 */
	@SchemaProperty(name = "群公告")
	@TableField("group_notice")
	private String groupNotice;

	/**
	 * 加入方式 (0 : 直接加入, 1 : 管理员同意后加入)
	 */
	@SchemaProperty(name = "加入方式 (0 : 直接加入, 1 : 管理员同意后加入)")
	@TableField("join_type")
	private Integer joinType;

	/**
	 * 状态 (1 : 正常 0 : 解散)
	 */
	@SchemaProperty(name = "状态 (1 : 正常 0 : 解散)")
	private Integer status;


    @Override
	public String toString (){
		return "群聊ID:"+(groupId == null ? "空" : groupId)+"，群组名:"+(groupName == null ? "空" : groupName)+"，群头像链接:"+(groupAvatar == null ? "空" : groupAvatar)+"，群主ID:"+(groupOwnerId == null ? "空" : groupOwnerId)+"，创建时间:"+(createTime == null ? "空" : DateUtil.format(createTime, DateTimePatternEnum.YYYY_MM_DD_HH_MM_SS.getPattern()))+"，群公告:"+(groupNotice == null ? "空" : groupNotice)+"，加入方式 (0 : 直接加入, 1 : 管理员同意后加入):"+(joinType == null ? "空" : joinType)+"，状态 (1 : 正常 0 : 解散):"+(status == null ? "空" : status);
	}
}
