package com.yaonie.intelligent.assessment.server.chat_server.user.controller;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.UserContactApply;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserContactApplyQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserContactApplyService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户人申请表 Controller
 * @author 77160
 */
@RestController("userContactApplyController")
@RequestMapping("/userContactApply")
public class UserContactApplyController extends ABaseController{

	@Resource
	private UserContactApplyService userContactApplyService;
	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public BaseResponse<?> loadDataList(UserContactApplyQuery query){
		return getSuccessResponseVO(userContactApplyService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public BaseResponse<?> add(UserContactApply bean) {
		userContactApplyService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public BaseResponse<?> addBatch(@RequestBody List<UserContactApply> listBean) {
		userContactApplyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public BaseResponse<?> addOrUpdateBatch(@RequestBody List<UserContactApply> listBean) {
		userContactApplyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyId查询对象
	 */
	@RequestMapping("/getByApplyId")
	public BaseResponse<?> getUserContactApplyByApplyId(Integer applyId) {
		return getSuccessResponseVO(userContactApplyService.getUserContactApplyByApplyId(applyId));
	}

	/**
	 * 根据ApplyId修改对象
	 */
	@RequestMapping("/updateByApplyId")
	public BaseResponse<?> updateUserContactApplyByApplyId(UserContactApply bean,Integer applyId) {
		userContactApplyService.updateUserContactApplyByApplyId(bean,applyId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyId删除
	 */
	@RequestMapping("/deleteByApplyId")
	public BaseResponse<?> deleteUserContactApplyByApplyId(Integer applyId) {
		userContactApplyService.deleteUserContactApplyByApplyId(applyId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId查询对象
	 */
	@RequestMapping("/getUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId")
	public BaseResponse<?> getUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(Long applyUserId,Long receiveUserId,Long concatId) {
		return getSuccessResponseVO(userContactApplyService.getUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(applyUserId,receiveUserId,concatId));
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId修改对象
	 */
	@RequestMapping("/updateUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId")
	public BaseResponse<?> updateUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(UserContactApply bean,Long applyUserId,Long receiveUserId,Long concatId) {
		userContactApplyService.updateUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(bean,applyUserId,receiveUserId,concatId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId删除
	 */
	@RequestMapping("/deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId")
	public BaseResponse<?> deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(Long applyUserId,Long receiveUserId,Long concatId) {
		userContactApplyService.deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(applyUserId,receiveUserId,concatId);
		return getSuccessResponseVO(null);
	}
}