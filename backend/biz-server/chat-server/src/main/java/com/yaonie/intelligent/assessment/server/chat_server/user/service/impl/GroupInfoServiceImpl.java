package com.yaonie.intelligent.assessment.server.chat_server.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.dto.SysSettingDto;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactStatusEnum;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactTypeEnum;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.GroupInfo;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.UserContact;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.GroupInfoQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.mappers.GroupInfoMapper;
import com.yaonie.intelligent.assessment.server.chat_server.user.mappers.UserContactMapper;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.GroupInfoService;
import com.yaonie.intelligent.assessment.server.chat_server.utils.StringTools;
import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


/**
 * 群聊存档 业务接口实现
 * @author 77160
 */
@Service("groupInfoService")
@Slf4j
public class GroupInfoServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfo> implements GroupInfoService {

	@Resource
	private GroupInfoMapper groupInfoMapper;

	@Resource
	private UserContactMapper userContactMapper;

	/**
	 * 新增
	 */
	@Override
	public Integer add(GroupInfo bean) {
		return this.groupInfoMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<GroupInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		List<BatchResult> insert = this.groupInfoMapper.insert(listBean);
		return insert.size();
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<GroupInfo> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		List<BatchResult> batchResults = groupInfoMapper.insertOrUpdate(listBean);
		return batchResults.size();
	}

	/**
	 * 根据GroupId获取对象
	 */
	@Override
	public GroupInfo getGroupInfoByGroupId(Long groupId) {
		return this.groupInfoMapper.selectByGroupId(groupId);
	}

	/**
	 * 根据GroupId修改
	 */
	@Override
	public Integer updateGroupInfoByGroupId(GroupInfo bean, Long groupId) {
		return this.groupInfoMapper.updateByGroupId(bean, groupId);
	}

	/**
	 * 根据GroupId删除
	 */
	@Override
	public Integer deleteGroupInfoByGroupId(Long groupId) {
		return this.groupInfoMapper.deleteByGroupId(groupId);
	}

	@Resource
	private SysSettingDto sysSetting;
	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer saveGroup(HttpServletRequest request, GroupInfo groupInfo) {
		if (groupInfo == null || groupInfo.getGroupAvatar() == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		User loginUser = UserHolder.getUser();
		if (groupInfo.getGroupId() == null) {
			// 新增群组
			GroupInfoQuery groupInfoQuery = new GroupInfoQuery();
			groupInfoQuery.setGroupOwnerId(groupInfo.getGroupOwnerId());
			Long count = lambdaQuery()
					.eq(GroupInfo::getGroupOwnerId, loginUser.getId())
					.eq(GroupInfo::getStatus, CommonConstant.IS_ENABLE)
					.count();
			if (count > sysSetting.getMaxGroupCount()) {
				throw new BusinessException(ErrorCode.GROUP_MAX_ERROR);
			}
			Long groupId = StringTools.getGroupId();
			groupInfo.setGroupId(groupId);
			groupInfo.setGroupOwnerId(loginUser.getId());
			// 将自己添加为群组联系人
			UserContact userContact = new UserContact();
			userContact.setStatus(UserContactStatusEnum.FRIEND.getStatus());
			userContact.setContactType(UserContactTypeEnum.GROUP.getType());
			userContact.setUserId(loginUser.getId());
			log.info("群组创建人:{}", userContact.getUserId());
			userContact.setContactId(groupInfo.getGroupId());
			userContactMapper.insert(userContact);
			// TODO: 创建会话
			// TODO: 发送消息
//			publisher.publishEvent();
//			avatarFile, avatarCover
			return add(groupInfo);
		} else {
			//loginUser.getId()
			GroupInfo dbGroupInfo = groupInfoMapper.selectByGroupId(groupInfo.getGroupId());
			ThrowUtils.throwIf(!Objects.equals(dbGroupInfo.getGroupOwnerId(), groupInfo.getGroupOwnerId()), ErrorCode.NO_AUTH_ERROR);
			this.updateGroupInfoByGroupId(groupInfo, groupInfo.getGroupId());
			// TODO: 更新相关表冗余信息
			// TODO: 修改群昵称发送WS消息
			return null;
		}
	}
}