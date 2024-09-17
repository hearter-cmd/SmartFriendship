package com.yaonie.intelligent.assessment.server.chat_server.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.feign.evaluation.EvaluationClient;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.query.UserQuery;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.PaginationResultVO;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.ResponseVO;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.dto.app.AppQueryRequest;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.model.model.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 用户 Controller
 * @Author 武春利
 */
@RestController("userController")
@RequestMapping("/user")
public class UserController extends ABaseController{

	@Resource
	private UserService userService;

	@Resource
	private EvaluationClient evaluationClient;

	@GetMapping("/setSession")
	@Operation(summary = "设置session")
	public BaseResponse setSession(HttpServletRequest request) {
		request.getSession().setAttribute("test", "test");
		return ResultUtils.success(null);
	}

	@GetMapping("/test")
	public BaseResponse<Page<UserVO>> test(HttpServletRequest request) {
		AppQueryRequest appQueryRequest = new AppQueryRequest();
		BaseResponse<Page<UserVO>> pageBaseResponse = evaluationClient.listAppVOByPage(appQueryRequest);
		return pageBaseResponse;
	}

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO<PaginationResultVO<User>> loadDataList(UserQuery query){
		return getSuccessResponseVO(userService.findListByPage(query));
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public ResponseVO add(User bean) {
		userService.add(bean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO<Integer> addBatch(@RequestBody List<User> listBean) {
		userService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 批量新增/修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO<Object> addOrUpdateBatch(@RequestBody List<User> listBean) {
		userService.addBatch(listBean);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id查询对象
	 */
	@RequestMapping("/getUserById")
	public ResponseVO<User> getUserById(Long id) {
		return getSuccessResponseVO(userService.getUserById(id));
	}

	/**
	 * 根据Id修改对象
	 */
	@RequestMapping("/updateUserById")
	public ResponseVO updateUserById(User bean,Long id) {
		userService.updateUserById(bean,id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Id删除
	 */
	@RequestMapping("/deleteUserById")
	public ResponseVO deleteUserById(Long id) {
		userService.deleteUserById(id);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email查询对象
	 */
	@RequestMapping("/getUserByEmail")
	public ResponseVO getUserByEmail(String email) {
		return getSuccessResponseVO(userService.getUserByEmail(email));
	}

	/**
	 * 根据Email修改对象
	 */
	@RequestMapping("/updateUserByEmail")
	public ResponseVO updateUserByEmail(User bean,String email) {
		userService.updateUserByEmail(bean,email);
		return getSuccessResponseVO(null);
	}

	/**
	 * 根据Email删除
	 */
	@RequestMapping("/deleteUserByEmail")
	public ResponseVO deleteUserByEmail(String email) {
		userService.deleteUserByEmail(email);
		return getSuccessResponseVO(null);
	}
}