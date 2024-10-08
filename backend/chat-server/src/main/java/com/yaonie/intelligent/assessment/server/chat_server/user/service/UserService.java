package com.yaonie.intelligent.assessment.server.chat_server.user.service;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.PaginationResultVO;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


/**
 * 用户 业务接口
 */
public interface UserService {

	/**
	 * 根据条件查询列表
	 */
	List<User> findListByParam(UserQuery param);

	/**
	 * 根据条件查询列表
	 */
	Integer findCountByParam(UserQuery param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<User> findListByPage(UserQuery param);

	/**
	 * 新增
	 */
	Long add(User bean);

	/**
	 * 批量新增
	 */
	Integer addBatch(List<User> listBean);

	/**
	 * 批量新增/修改
	 */
	Integer addOrUpdateBatch(List<User> listBean);

	/**
	 * 多条件更新
	 */
	Integer updateByParam(User bean,UserQuery param);

	/**
	 * 多条件删除
	 */
	Integer deleteByParam(UserQuery param);

	/**
	 * 根据Id查询对象
	 */
	User getUserById(Long id);


	/**
	 * 根据Id修改
	 */
	Integer updateUserById(User bean,Long id);


	/**
	 * 根据Id删除
	 */
	Integer deleteUserById(Long id);


	/**
	 * 根据Email查询对象
	 */
	User getUserByEmail(String email);


	/**
	 * 根据Email修改
	 */
	Integer updateUserByEmail(User bean,String email);


	/**
	 * 根据Email删除
	 */
	Integer deleteUserByEmail(String email);

	User getLoginUser(HttpServletRequest request);

	User getUserByMpOpenId(String mpOpenId);
}