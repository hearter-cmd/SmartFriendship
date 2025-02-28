package com.yaonie.intelligent.assessment.server.chat_server.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.PageSize;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactStatusEnum;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.UserContact;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.SimplePage;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserContactQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.PaginationResultVO;
import com.yaonie.intelligent.assessment.server.chat_server.user.mappers.UserContactMapper;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserContactService;
import com.yaonie.intelligent.assessment.server.common.model.constant.CommonConstant;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import com.yaonie.intelligent.assessment.system.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.apache.ibatis.executor.BatchResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 联系人关系表 业务接口实现
 * @author 77160
 */
@Service("userContactService")
public class UserContactServiceImpl extends ServiceImpl<UserContactMapper, UserContact> implements UserContactService {

	@Resource
	private UserContactMapper userContactMapper;
	@Resource
	private UserMapper userMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<UserContact> findListByParam(UserContactQuery param) {
		List<UserContact> list = lambdaQuery()
				.eq(UserContact::getContactId, param.getContactId())
				.eq(UserContact::getContactType, param.getContactType())
				.eq(UserContact::getStatus, param.getStatus())
				.eq(UserContact::getUserId, param.getUserId())
				.list();
		return list;
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(UserContact param) {
		Long count = lambdaQuery().eq(param.getUserId() != null, UserContact::getUserId, param.getUserId())
				.eq(param.getContactId() != null, UserContact::getContactId, param.getContactId())
				.eq(param.getContactType() != null, UserContact::getContactType, param.getContactType())
				.eq(param.getStatus() != null, UserContact::getStatus, param.getStatus())
				.count();
		return count.intValue();
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<UserContact> findListByPage(UserContactQuery param) {
		UserContact userContact = new UserContact();
		BeanUtils.copyProperties(param, userContact);
		int count = findCountByParam(userContact);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<UserContact> list = findListByParam(param);
		PaginationResultVO<UserContact> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(UserContact bean) {
		return userContactMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<UserContact> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		List<BatchResult> insert = userContactMapper.insert(listBean);
		return insert.size();
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<UserContact> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return userContactMapper.insertOrUpdate(listBean).size();
	}

	/**
	 * 根据UserIdAndContactId获取对象
	 */
	@Override
	public UserContact getUserContactByUserIdAndContactId(Long userId, Long contactId) {
		return this.userContactMapper.selectByUserIdAndContactId(userId, contactId);
	}

	/**
	 * 根据UserIdAndContactId修改
	 */
	@Override
	public Integer updateUserContactByUserIdAndContactId(UserContact bean, Long userId, Long contactId) {
		return this.userContactMapper.updateByUserIdAndContactId(bean, userId, contactId);
	}

	/**
	 * 根据UserIdAndContactId删除
	 */
	@Override
	public Integer deleteUserContactByUserIdAndContactId(Long userId, Long contactId) {
		return userContactMapper.deleteByUserIdAndContactId(userId, contactId);
	}

	@Override
	public List<UserContact> findList() {
		Long userId = SecurityUtils.getUserId();
		List<UserContact> list = lambdaQuery()
				.eq(UserContact::getUserId, userId)
				.eq(UserContact::getStatus, UserContactStatusEnum.FRIEND.getStatus())
				.list();
		List<Long> contactIds = list.stream().map(UserContact::getContactId).toList();
		LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper
				.in(User::getId, contactIds)
				.eq(User::getEnable, CommonConstant.IS_ENABLE)
				.eq(User::getIsDelete, CommonConstant.IS_NOT_DELETED)
				.select(User::getId, User::getUserName, User::getUserAvatar);
		List<User> users = userMapper.selectList(lambdaQueryWrapper);
		list.forEach(userContact -> {
			users.forEach(user -> {
				if (user.getId().equals(userContact.getContactId())) {
					userContact.setAvatar(user.getUserAvatar());
					userContact.setName(user.getUserName());
				}
			});
		});
		return list;
	}
}