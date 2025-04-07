package com.yaonie.intelligent.assessment.server.chat_server.user.controller;

import com.yaonie.intelligent.assessment.server.chat_server.user.entity.dto.ContactApplyRequest;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.vo.UserContactApplyVO;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserContactApplyService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户人申请表 Controller
 *
 * @author 77160
 */
@Slf4j
@RestController("userContactApplyController")
@RequestMapping("/userContactApply")
public class UserContactApplyController extends ABaseController {

    @Resource
    private UserContactApplyService userContactApplyService;

    @PostMapping
    public BaseResponse<String> sendContactApply(@RequestBody @Valid ContactApplyRequest request) {
        userContactApplyService.sendContactApply(request);
        return ResultUtils.success("申请成功");
    }

    @GetMapping
    public BaseResponse<List<UserContactApplyVO>> getContactApplyList() {
        return ResultUtils.success(userContactApplyService.getOwnContactApplyList());
    }

    @GetMapping("/accept/{id}")
    public BaseResponse<String> acceptContactApply(@PathVariable("id") Long id) {
        userContactApplyService.acceptContactApply(id);
        return ResultUtils.success("同意申请成功！");
    }

    @GetMapping("/reject/{id}")
    public BaseResponse<String> rejectContactApply(@PathVariable("id") Long id) {
        userContactApplyService.rejectContactApply(id);
        return ResultUtils.success("拒绝申请成功！");
    }

}