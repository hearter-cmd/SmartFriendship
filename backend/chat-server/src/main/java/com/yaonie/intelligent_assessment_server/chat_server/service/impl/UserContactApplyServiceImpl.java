package com.yaonie.intelligent_assessment_server.chat_server.service.impl;

import java.util.List;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import com.yaonie.intelligent_assessment_server.chat_server.entity.enums.PageSize;
import com.yaonie.intelligent_assessment_server.chat_server.entity.query.UserContactApplyQuery;
import com.yaonie.intelligent_assessment_server.chat_server.entity.po.UserContactApply;
import com.yaonie.intelligent_assessment_server.chat_server.entity.vo.PaginationResultVO;
import com.yaonie.intelligent_assessment_server.chat_server.entity.query.SimplePage;
import com.yaonie.intelligent_assessment_server.chat_server.mappers.UserContactApplyMapper;
import com.yaonie.intelligent_assessment_server.chat_server.service.UserContactApplyService;
import com.yaonie.intelligent_assessment_server.chat_server.utils.StringTools;


/**
 * 用户人申请表 业务接口实现
 */
@Service("userContactApplyService")
public class UserContactApplyServiceImpl implements UserContactApplyService {

	@Resource
	private UserContactApplyMapper<UserContactApply, UserContactApplyQuery> userContactApplyMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<UserContactApply> findListByParam(UserContactApplyQuery param) {
		return this.userContactApplyMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(UserContactApplyQuery param) {
		return this.userContactApplyMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<UserContactApply> findListByPage(UserContactApplyQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<UserContactApply> list = this.findListByParam(param);
		PaginationResultVO<UserContactApply> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(UserContactApply bean) {
		return this.userContactApplyMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<UserContactApply> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userContactApplyMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<UserContactApply> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userContactApplyMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(UserContactApply bean, UserContactApplyQuery param) {
		StringTools.checkParam(param);
		return this.userContactApplyMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(UserContactApplyQuery param) {
		StringTools.checkParam(param);
		return this.userContactApplyMapper.deleteByParam(param);
	}

	/**
	 * 根据ApplyId获取对象
	 */
	@Override
	public UserContactApply getUserContactApplyByApplyId(Integer applyId) {
		return this.userContactApplyMapper.selectByApplyId(applyId);
	}

	/**
	 * 根据ApplyId修改
	 */
	@Override
	public Integer updateUserContactApplyByApplyId(UserContactApply bean, Integer applyId) {
		return this.userContactApplyMapper.updateByApplyId(bean, applyId);
	}

	/**
	 * 根据ApplyId删除
	 */
	@Override
	public Integer deleteUserContactApplyByApplyId(Integer applyId) {
		return this.userContactApplyMapper.deleteByApplyId(applyId);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId获取对象
	 */
	@Override
	public UserContactApply getUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(Long applyUserId, Long receiveUserId, Long concatId) {
		return this.userContactApplyMapper.selectByApplyUserIdAndReceiveUserIdAndConcatId(applyUserId, receiveUserId, concatId);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId修改
	 */
	@Override
	public Integer updateUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(UserContactApply bean, Long applyUserId, Long receiveUserId, Long concatId) {
		return this.userContactApplyMapper.updateByApplyUserIdAndReceiveUserIdAndConcatId(bean, applyUserId, receiveUserId, concatId);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId删除
	 */
	@Override
	public Integer deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(Long applyUserId, Long receiveUserId, Long concatId) {
		return this.userContactApplyMapper.deleteByApplyUserIdAndReceiveUserIdAndConcatId(applyUserId, receiveUserId, concatId);
	}
}