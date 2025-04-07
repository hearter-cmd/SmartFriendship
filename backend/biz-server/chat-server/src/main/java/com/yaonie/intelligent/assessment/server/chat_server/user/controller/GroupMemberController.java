package com.yaonie.intelligent.assessment.server.chat_server.user.controller;


import com.yaonie.intelligent.assessment.server.chat_server.common.service.GroupMemberService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-03-25
 */
@RestController
@RequestMapping("/groupMember")
public class GroupMemberController {

    @Resource
    private GroupMemberService groupMemberService;

    /**
     * 检查是否已经是群成员
     *
     * @param groupId
     * @return
     */
    @GetMapping("/check/{groupId}")
    public BaseResponse<?> checkGroupMember(@PathVariable("groupId") Long groupId) {
        boolean b = groupMemberService.checkGroupMember(groupId);
        Long memberCount = groupMemberService.getGroupMemberCount(groupId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("isMember", b);
        result.put("memberCount", memberCount);
        return ResultUtils.success(result);
    }
}
