package com.yaonie.intelligent.assessment.server.chat_server.user.service;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.UserBeauty;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserBeautyQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.PaginationResultVO;

import java.util.List;


/**
 * 用户 业务接口
 */
public interface UserBeautyService {

	/**
	 * 根据条件查询列表
	 */
	List<UserBeauty> findListByParam(UserBeautyQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(UserBeautyQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<UserBeauty> findListByPage(UserBeautyQuery param);

	/**
	 * 新增
	 */
	Integer add(UserBeauty bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<UserBeauty> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<UserBeauty> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(UserBeauty bean,UserBeautyQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(UserBeautyQuery param);

	/**
	 * 根据Id查询对象
	 */
	UserBeauty getUserBeautyById(Long id);


	/**
	 * 根据Id修改
	 */
	Integer updateUserBeautyById(UserBeauty bean,Long id);


	/**
	 * 根据Id删除
	 */
	Integer deleteUserBeautyById(Long id);


	/**
	 * 根据Email查询对象
	 */
	UserBeauty getUserBeautyByEmail(String email);


	/**
	 * 根据Email修改
	 */
	Integer updateUserBeautyByEmail(UserBeauty bean,String email);


	/**
	 * 根据Email删除
	 */
	Integer deleteUserBeautyByEmail(String email);

}