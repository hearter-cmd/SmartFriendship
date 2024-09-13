package com.yaonie.intelligent_assessment_server.chat_server.service.impl;

import com.yaonie.intelligent_assessment_server.chat_server.entity.enums.PageSize;
import com.yaonie.intelligent_assessment_server.chat_server.entity.po.User;
import com.yaonie.intelligent_assessment_server.chat_server.entity.query.SimplePage;
import com.yaonie.intelligent_assessment_server.chat_server.entity.query.UserQuery;
import com.yaonie.intelligent_assessment_server.chat_server.entity.vo.PaginationResultVO;
import com.yaonie.intelligent_assessment_server.chat_server.mappers.UserMapper;
import com.yaonie.intelligent_assessment_server.chat_server.service.UserService;
import com.yaonie.intelligent_assessment_server.chat_server.utils.StringTools;
import com.yaonie.intelligent_assessment_server.common.ErrorCode;
import com.yaonie.intelligent_assessment_server.exception.BusinessException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.yaonie.intelligent_assessment_server.constant.UserConstant.USER_LOGIN_STATE;


/**
 * 用户 业务接口实现
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper<User, UserQuery> userMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<User> findListByParam(UserQuery param) {
		return this.userMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(UserQuery param) {
		return this.userMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<User> findListByPage(UserQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<User> list = this.findListByParam(param);
		PaginationResultVO<User> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(User bean) {
		return this.userMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<User> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<User> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(User bean, UserQuery param) {
		StringTools.checkParam(param);
		return this.userMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(UserQuery param) {
		StringTools.checkParam(param);
		return this.userMapper.deleteByParam(param);
	}

	/**
	 * 根据Id获取对象
	 */
	@Override
	public User getUserById(Long id) {
		return this.userMapper.selectById(id);
	}

	/**
	 * 根据Id修改
	 */
	@Override
	public Integer updateUserById(User bean, Long id) {
		return this.userMapper.updateById(bean, id);
	}

	/**
	 * 根据Id删除
	 */
	@Override
	public Integer deleteUserById(Long id) {
		return this.userMapper.deleteById(id);
	}

	/**
	 * 根据Email获取对象
	 */
	@Override
	public User getUserByEmail(String email) {
		return this.userMapper.selectByEmail(email);
	}

	/**
	 * 根据Email修改
	 */
	@Override
	public Integer updateUserByEmail(User bean, String email) {
		return this.userMapper.updateByEmail(bean, email);
	}

	/**
	 * 根据Email删除
	 */
	@Override
	public Integer deleteUserByEmail(String email) {
		return this.userMapper.deleteByEmail(email);
	}

	/**
	 * 获取当前登录用户
	 *
	 * @param request
	 * @return
	 */
	@Override
	public User getLoginUser(HttpServletRequest request) {
		// 先判断是否已登录
		Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
		User currentUser = (User) userObj;
		if (currentUser == null || currentUser.getId() == null) {
			throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
		}
		// TODO 从数据库查询（追求性能的话可以注释，直接走缓存）
		long userId = currentUser.getId();
		currentUser = this.getUserById(userId);
		if (currentUser == null) {
			throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
		}
		return currentUser;
	}
}