package com.yaonie.intelligent.assessment.server.chat_server.user.controller;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.UserBeauty;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserBeautyQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserBeautyService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 用户 Controller
 */
@RestController("userBeautyController")
@RequestMapping("/userBeauty")
public class UserBeautyController extends ABaseController{

	@Resource
	private UserBeautyService userBeautyService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public BaseResponse<?> loadDataList(UserBeautyQuery query){
		return getSuccessResponseVO(userBeautyService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public BaseResponse<?> add(UserBeauty bean) {
		userBeautyService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public BaseResponse<?> addBatch(@RequestBody List<UserBeauty> listBean) {
		userBeautyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public BaseResponse<?> addOrUpdateBatch(@RequestBody List<UserBeauty> listBean) {
		userBeautyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id查询对象
	 */
	@RequestMapping("/getById")
	public BaseResponse<?> getUserBeautyById(Long id) {
		return getSuccessResponseVO(userBeautyService.getUserBeautyById(id));
	}

	/**
	 * 根据Id修改对象
	 */
	@RequestMapping("/updateById")
	public BaseResponse<?> updateUserBeautyById(UserBeauty bean,Long id) {
		userBeautyService.updateUserBeautyById(bean,id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id删除
	 */
	@RequestMapping("/deleteById")
	public BaseResponse<?> deleteUserBeautyById(Long id) {
		userBeautyService.deleteUserBeautyById(id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email查询对象
	 */
	@RequestMapping("/getByEmail")
	public BaseResponse<?> getUserBeautyByEmail(String email) {
		return getSuccessResponseVO(userBeautyService.getUserBeautyByEmail(email));
	}

	/**
	 * 根据Email修改对象
	 */
	@RequestMapping("/updateByEmail")
	public BaseResponse<?> updateUserBeautyByEmail(UserBeauty bean,String email) {
		userBeautyService.updateUserBeautyByEmail(bean,email);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email删除
	 */
	@RequestMapping("/deleteByEmail")
	public BaseResponse<?> deleteUserBeautyByEmail(String email) {
		userBeautyService.deleteUserBeautyByEmail(email);
		return getSuccessResponseVO(null);
	}
}