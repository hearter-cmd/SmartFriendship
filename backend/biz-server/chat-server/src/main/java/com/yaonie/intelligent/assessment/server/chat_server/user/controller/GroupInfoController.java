package com.yaonie.intelligent.assessment.server.chat_server.user.controller;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.po.GroupInfo;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.GroupInfoQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.GroupInfoService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 群聊存档 Controller
 * @author 77160
 */
@Slf4j
@RestController("groupInfoController")
@RequestMapping("/groupInfo")
public class GroupInfoController extends ABaseController{

	@Resource
	private GroupInfoService groupInfoService;

	/**
	 * 保存群
	 * TODO:
	 * 	这里的头像用腾讯COS保存, 然后获取URL, 进行访问
	 * @param groupInfo 群信息
	 * @param request 请求
	 * @return ResponseVO 响应
	 */
	@PostMapping("/saveGroup")
	@Operation(summary = "保存群组")
//	@Auth
	public BaseResponse<?> saveGroup(@RequestBody GroupInfo groupInfo,
										  HttpServletRequest request) {
		return getSuccessResponseVO(groupInfoService.saveGroup(request, groupInfo));
	}

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public BaseResponse<?> loadDataList(@RequestBody GroupInfoQuery query){
		return getSuccessResponseVO(groupInfoService.findListByPage(query));
	}

	/**
	 * 根据GroupId查询对象
	 */
	@RequestMapping("/getGroupInfoByGroupId")
	public BaseResponse<?> getGroupInfoByGroupId(Long groupId) {
		return getSuccessResponseVO(groupInfoService.getGroupInfoByGroupId(groupId));
	}

	/**
	 * 根据GroupId修改对象
	 */
	@RequestMapping("/updateGroupInfoByGroupId")
	public BaseResponse<?> updateGroupInfoByGroupId(GroupInfo bean,Long groupId) {
		groupInfoService.updateGroupInfoByGroupId(bean,groupId);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据GroupId删除
	 */
	@RequestMapping("/deleteGroupInfoByGroupId")
	public BaseResponse<?> deleteGroupInfoByGroupId(Long groupId) {
		groupInfoService.deleteGroupInfoByGroupId(groupId);
		return getSuccessResponseVO(null);
	}
}