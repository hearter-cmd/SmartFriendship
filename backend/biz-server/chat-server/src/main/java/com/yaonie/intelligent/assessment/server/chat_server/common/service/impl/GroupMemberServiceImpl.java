package com.yaonie.intelligent.assessment.server.chat_server.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.chat_server.common.mappers.GroupMemberMapper;
import com.yaonie.intelligent.assessment.server.chat_server.common.service.GroupMemberService;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactStatusEnum;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.impl.UserContactServiceImpl;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupMember;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.UserContact;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * @author 77160
 * @description 针对表【group_member】的数据库操作Service实现
 * @createDate 2024-09-24 17:15:15
 */
@Service
public class GroupMemberServiceImpl extends ServiceImpl<GroupMemberMapper, GroupMember>
        implements GroupMemberService {
    private final UserContactServiceImpl userContactService;

    public GroupMemberServiceImpl(UserContactServiceImpl userContactService) {
        this.userContactService = userContactService;
    }

    @Override
    public boolean checkGroupMember(Long groupId) {
        Long userId = SecurityUtils.getUserId();
        Long count = userContactService.lambdaQuery()
                .eq(UserContact::getUserId, userId)
                .eq(UserContact::getContactId, groupId)
                .eq(UserContact::getStatus, UserContactStatusEnum.FRIEND.getStatus())
                .count();
        return count > 0;
    }

    @Override
    public Long getGroupMemberCount(Long groupId) {
        Long count = userContactService.lambdaQuery()
                .eq(UserContact::getContactId, groupId)
                .eq(UserContact::getStatus, UserContactStatusEnum.FRIEND.getStatus())
                .count();
        return count;
    }
}




