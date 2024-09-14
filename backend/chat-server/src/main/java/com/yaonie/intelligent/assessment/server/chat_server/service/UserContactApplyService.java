package com.yaonie.intelligent.assessment.server.chat_server.service;

import java.util.List;

import com.yaonie.intelligent.assessment.server.chat_server.entity.query.UserContactApplyQuery;
import com.yaonie.intelligent.assessment.server.chat_server.entity.po.UserContactApply;
import com.yaonie.intelligent.assessment.server.chat_server.entity.vo.PaginationResultVO;


/**
 * 用户人申请表 业务接口
 */
public interface UserContactApplyService {

	/**
	 * 根据条件查询列表
	 */
	List<UserContactApply> findListByParam(UserContactApplyQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(UserContactApplyQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<UserContactApply> findListByPage(UserContactApplyQuery param);

	/**
	 * 新增
	 */
	Integer add(UserContactApply bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<UserContactApply> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<UserContactApply> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(UserContactApply bean,UserContactApplyQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(UserContactApplyQuery param);

	/**
	 * 根据ApplyId查询对象
	 */
	UserContactApply getUserContactApplyByApplyId(Integer applyId);


	/**
	 * 根据ApplyId修改
	 */
	Integer updateUserContactApplyByApplyId(UserContactApply bean,Integer applyId);


	/**
	 * 根据ApplyId删除
	 */
	Integer deleteUserContactApplyByApplyId(Integer applyId);


	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId查询对象
	 */
	UserContactApply getUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(Long applyUserId,Long receiveUserId,Long concatId);


	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId修改
	 */
	Integer updateUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(UserContactApply bean,Long applyUserId,Long receiveUserId,Long concatId);


	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId删除
	 */
	Integer deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(Long applyUserId,Long receiveUserId,Long concatId);

}