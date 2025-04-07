package com.yaonie.intelligent.assessment.server.chat_server.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.GroupMember;


/**
 * @author 77160
 * @description 针对表【group_member】的数据库操作Service
 * @createDate 2024-09-24 17:15:15
 */
public interface GroupMemberService extends IService<GroupMember> {

    boolean checkGroupMember(Long groupId);

    Long getGroupMemberCount(Long groupId);
}
