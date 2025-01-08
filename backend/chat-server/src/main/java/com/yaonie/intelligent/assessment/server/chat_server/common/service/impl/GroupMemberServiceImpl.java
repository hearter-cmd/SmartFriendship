package com.yaonie.intelligent.assessment.server.chat_server.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.chat_server.common.mappers.GroupMemberMapper;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.GroupMember;
import com.yaonie.intelligent.assessment.server.chat_server.common.service.GroupMemberService;
import org.springframework.stereotype.Service;

/**
 * @author 77160
 * @description 针对表【group_member】的数据库操作Service实现
 * @createDate 2024-09-24 17:15:15
 */
@Service
public class GroupMemberServiceImpl extends ServiceImpl<GroupMemberMapper, GroupMember>
        implements GroupMemberService {
}




