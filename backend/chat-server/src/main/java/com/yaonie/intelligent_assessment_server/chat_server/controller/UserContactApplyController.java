package com.yaonie.intelligent_assessment_server.chat_server.controller;

import com.yaonie.intelligent_assessment_server.chat_server.entity.po.UserContactApply;
import com.yaonie.intelligent_assessment_server.chat_server.entity.query.UserContactApplyQuery;
import com.yaonie.intelligent_assessment_server.chat_server.entity.vo.ResponseVO;
import com.yaonie.intelligent_assessment_server.chat_server.service.UserContactApplyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户人申请表 Controller
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
	public ResponseVO loadDataList(UserContactApplyQuery query){
		return getSuccessResponseVO(userContactApplyService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(UserContactApply bean) {
		userContactApplyService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<UserContactApply> listBean) {
		userContactApplyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<UserContactApply> listBean) {
		userContactApplyService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyId查询对象
	 */
	@RequestMapping("/getUserContactApplyByApplyId")
	public ResponseVO getUserContactApplyByApplyId(Integer applyId) {
		return getSuccessResponseVO(userContactApplyService.getUserContactApplyByApplyId(applyId));
	}

	/**
	 * 根据ApplyId修改对象
	 */
	@RequestMapping("/updateUserContactApplyByApplyId")
	public ResponseVO updateUserContactApplyByApplyId(UserContactApply bean,Integer applyId) {
		userContactApplyService.updateUserContactApplyByApplyId(bean,applyId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyId删除
	 */
	@RequestMapping("/deleteUserContactApplyByApplyId")
	public ResponseVO deleteUserContactApplyByApplyId(Integer applyId) {
		userContactApplyService.deleteUserContactApplyByApplyId(applyId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId查询对象
	 */
	@RequestMapping("/getUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId")
	public ResponseVO getUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(Long applyUserId,Long receiveUserId,Long concatId) {
		return getSuccessResponseVO(userContactApplyService.getUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(applyUserId,receiveUserId,concatId));
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId修改对象
	 */
	@RequestMapping("/updateUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId")
	public ResponseVO updateUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(UserContactApply bean,Long applyUserId,Long receiveUserId,Long concatId) {
		userContactApplyService.updateUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(bean,applyUserId,receiveUserId,concatId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据ApplyUserIdAndReceiveUserIdAndConcatId删除
	 */
	@RequestMapping("/deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId")
	public ResponseVO deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(Long applyUserId,Long receiveUserId,Long concatId) {
		userContactApplyService.deleteUserContactApplyByApplyUserIdAndReceiveUserIdAndConcatId(applyUserId,receiveUserId,concatId);
		return getSuccessResponseVO(null);
	}
}