package com.yaonie.intelligent.assessment.server.chat_server.user.controller;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.UserContact;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserContactQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserContactService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 联系人关系表 Controller
 * @author 武春利
 * @since 2025-01-23
 */
@RestController("userContactController")
@RequestMapping("/userContact")
public class UserContactController extends ABaseController{

	@Resource
	private UserContactService userContactService;
	/**
	 * 根据条件分页查询
	 */
	@GetMapping("/loadDataList")
	public BaseResponse<?> loadDataListByPage(UserContactQuery query){
		return getSuccessResponseVO(userContactService.findListByPage(query));
	}

	/**
	 * 根据条件查询
	 */
	@GetMapping("/loadContactList")
	public BaseResponse<?> loadDataList(){
		return getSuccessResponseVO(userContactService.findList());
	}


	/**
	 * 新增
	 */
	@PostMapping("/add")
	public BaseResponse<?> add(UserContact bean) {
		userContactService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@PostMapping("/addBatch")
	public BaseResponse<?> addBatch(@RequestBody List<UserContact> listBean) {
		userContactService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public BaseResponse<?> addOrUpdateBatch(@RequestBody List<UserContact> listBean) {
		userContactService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据UserIdAndContactId查询对象
	 */
	@RequestMapping("/getByUserIdAndContactId")
	public BaseResponse<?> getUserContactByUserIdAndContactId(Long userId,Long contactId) {
		return getSuccessResponseVO(userContactService.getUserContactByUserIdAndContactId(userId,contactId));
	}

	/**
	 * 根据UserIdAndContactId修改对象
	 */
	@RequestMapping("/updateByUserIdAndContactId")
	public BaseResponse<?> updateUserContactByUserIdAndContactId(UserContact bean,Long userId,Long contactId) {
		userContactService.updateUserContactByUserIdAndContactId(bean,userId,contactId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据UserIdAndContactId删除
	 */
	@RequestMapping("/deleteByUserIdAndContactId")
	public BaseResponse<?> deleteUserContactByUserIdAndContactId(Long userId,Long contactId) {
		userContactService.deleteUserContactByUserIdAndContactId(userId,contactId);
		return getSuccessResponseVO(null);
	}
}