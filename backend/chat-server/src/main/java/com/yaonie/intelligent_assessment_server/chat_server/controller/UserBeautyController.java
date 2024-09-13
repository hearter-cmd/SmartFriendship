package com.yaonie.intelligent_assessment_server.chat_server.controller;

import com.yaonie.intelligent_assessment_server.chat_server.entity.po.UserBeauty;
import com.yaonie.intelligent_assessment_server.chat_server.entity.query.UserBeautyQuery;
import com.yaonie.intelligent_assessment_server.chat_server.entity.vo.ResponseVO;
import com.yaonie.intelligent_assessment_server.chat_server.service.UserBeautyService;
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
	public ResponseVO loadDataList(UserBeautyQuery query){
		return getSuccessResponseVO(userBeautyService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(UserBeauty bean) {
		userBeautyService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<UserBeauty> listBean) {
		userBeautyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<UserBeauty> listBean) {
		userBeautyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id查询对象
	 */
	@RequestMapping("/getUserBeautyById")
	public ResponseVO getUserBeautyById(Long id) {
		return getSuccessResponseVO(userBeautyService.getUserBeautyById(id));
	}

	/**
	 * 根据Id修改对象
	 */
	@RequestMapping("/updateUserBeautyById")
	public ResponseVO updateUserBeautyById(UserBeauty bean,Long id) {
		userBeautyService.updateUserBeautyById(bean,id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id删除
	 */
	@RequestMapping("/deleteUserBeautyById")
	public ResponseVO deleteUserBeautyById(Long id) {
		userBeautyService.deleteUserBeautyById(id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email查询对象
	 */
	@RequestMapping("/getUserBeautyByEmail")
	public ResponseVO getUserBeautyByEmail(String email) {
		return getSuccessResponseVO(userBeautyService.getUserBeautyByEmail(email));
	}

	/**
	 * 根据Email修改对象
	 */
	@RequestMapping("/updateUserBeautyByEmail")
	public ResponseVO updateUserBeautyByEmail(UserBeauty bean,String email) {
		userBeautyService.updateUserBeautyByEmail(bean,email);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email删除
	 */
	@RequestMapping("/deleteUserBeautyByEmail")
	public ResponseVO deleteUserBeautyByEmail(String email) {
		userBeautyService.deleteUserBeautyByEmail(email);
		return getSuccessResponseVO(null);
	}
}