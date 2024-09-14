package com.yaonie.intelligent.assessment.server.springbootinit.controller;

import com.yaonie.intelligent.assessment.server.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.model.dto.postthumb.PostThumbAddRequest;
import com.yaonie.intelligent.assessment.server.model.entity.User;
import com.yaonie.intelligent.assessment.server.springbootinit.service.PostThumbService;
import com.yaonie.intelligent.assessment.server.springbootinit.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 帖子点赞接口
 *
 * @author 77160
 */
@RestController
@RequestMapping("/post_thumb")
@Slf4j
public class PostThumbController {

    @Resource
    private PostThumbService postThumbService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param postThumbAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Integer> doThumb(@RequestBody PostThumbAddRequest postThumbAddRequest,
            HttpServletRequest request) {
        if (postThumbAddRequest == null || postThumbAddRequest.getPostId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long postId = postThumbAddRequest.getPostId();
        int result = postThumbService.doPostThumb(postId, loginUser);
        return ResultUtils.success(result);
    }

}
