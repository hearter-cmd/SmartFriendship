package com.yaonie.intelligent.assessment.server.chat_server.user.service.impl;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.PageSize;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.UserBeauty;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.SimplePage;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserBeautyQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.PaginationResultVO;
import com.yaonie.intelligent.assessment.server.chat_server.user.mappers.UserBeautyMapper;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserBeautyService;
import com.yaonie.intelligent.assessment.server.chat_server.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用户 业务接口实现
 */
@Service("userBeautyService")
public class UserBeautyServiceImpl implements UserBeautyService {

	@Resource
	private UserBeautyMapper<UserBeauty, UserBeautyQuery> userBeautyMapper;

	/**
	 * 根据条件查询列表
	 */
	@Override
	public List<UserBeauty> findListByParam(UserBeautyQuery param) {
		return this.userBeautyMapper.selectList(param);
	}

	/**
	 * 根据条件查询列表
	 */
	@Override
	public Integer findCountByParam(UserBeautyQuery param) {
		return this.userBeautyMapper.selectCount(param);
	}

	/**
	 * 分页查询方法
	 */
	@Override
	public PaginationResultVO<UserBeauty> findListByPage(UserBeautyQuery param) {
		int count = this.findCountByParam(param);
		int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

		SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
		param.setSimplePage(page);
		List<UserBeauty> list = this.findListByParam(param);
		PaginationResultVO<UserBeauty> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	@Override
	public Integer add(UserBeauty bean) {
		return this.userBeautyMapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	@Override
	public Integer addBatch(List<UserBeauty> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userBeautyMapper.insertBatch(listBean);
	}

	/**
	 * 批量新增或者修改
	 */
	@Override
	public Integer addOrUpdateBatch(List<UserBeauty> listBean) {
		if (listBean == null || listBean.isEmpty()) {
			return 0;
		}
		return this.userBeautyMapper.insertOrUpdateBatch(listBean);
	}

	/**
	 * 多条件更新
	 */
	@Override
	public Integer updateByParam(UserBeauty bean, UserBeautyQuery param) {
		StringTools.checkParam(param);
		return this.userBeautyMapper.updateByParam(bean, param);
	}

	/**
	 * 多条件删除
	 */
	@Override
	public Integer deleteByParam(UserBeautyQuery param) {
		StringTools.checkParam(param);
		return this.userBeautyMapper.deleteByParam(param);
	}

	/**
	 * 根据Id获取对象
	 */
	@Override
	public UserBeauty getUserBeautyById(Long id) {
		return this.userBeautyMapper.selectById(id);
	}

	/**
	 * 根据Id修改
	 */
	@Override
	public Integer updateUserBeautyById(UserBeauty bean, Long id) {
		return this.userBeautyMapper.updateById(bean, id);
	}

	/**
	 * 根据Id删除
	 */
	@Override
	public Integer deleteUserBeautyById(Long id) {
		return this.userBeautyMapper.deleteById(id);
	}

	/**
	 * 根据Email获取对象
	 */
	@Override
	public UserBeauty getUserBeautyByEmail(String email) {
		return this.userBeautyMapper.selectByEmail(email);
	}

	/**
	 * 根据Email修改
	 */
	@Override
	public Integer updateUserBeautyByEmail(UserBeauty bean, String email) {
		return this.userBeautyMapper.updateByEmail(bean, email);
	}

	/**
	 * 根据Email删除
	 */
	@Override
	public Integer deleteUserBeautyByEmail(String email) {
		return this.userBeautyMapper.deleteByEmail(email);
	}
}