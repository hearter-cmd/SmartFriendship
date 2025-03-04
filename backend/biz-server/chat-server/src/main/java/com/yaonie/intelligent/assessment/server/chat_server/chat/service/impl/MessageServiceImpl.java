package com.yaonie.intelligent.assessment.server.chat_server.chat.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.chat_server.chat.event.SendMessageEvent;
import com.yaonie.intelligent.assessment.server.chat_server.chat.mappers.MessageMapper;
import com.yaonie.intelligent.assessment.server.chat_server.chat.model.dto.MessageDto;
import com.yaonie.intelligent.assessment.server.chat_server.chat.service.MessageService;
import com.yaonie.intelligent.assessment.server.chat_server.common.mappers.GroupMemberMapper;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.FriendMessage;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.GroupMember;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.GroupMessage;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.Message;
import com.yaonie.intelligent.assessment.server.chat_server.user.entity.enums.UserContactTypeEnum;
import com.yaonie.intelligent.assessment.server.common.holder.UserHolder;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.server.common.util.SecurityUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 21:27
 * @Description : 聊天消息服务实现类
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Resource
    private GroupMemberMapper groupMemberMapper;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMsg(MessageDto msg, HttpServletRequest request) {
        // 转换
        User loginUser = UserHolder.getUser();
        Message message = new Message();
        BeanUtil.copyProperties(msg, message);
        message.setUserId(loginUser.getId());
        // 消息推送
        publisher.publishEvent(new SendMessageEvent(this, message));
    }

    @Override
    public Page<Message> getMsgList(Long id, HttpServletRequest request) {
        Page<Message> page = null;
        // 获取好友聊天记录
        switch(UserContactTypeEnum.getEnumByLen(id)) {
            case GROUP -> {
                page = lambdaQuery()
                        .eq(Message::getContactId, id)
                        .orderBy(true, false, Message::getCreateTime)
                        .page(new Page<>(1, 10));
            }
            case USER -> {
                User userInfo = SecurityUtils.getLoginUser();
                page = lambdaQuery()
                        .or(qw -> qw.eq(Message::getUserId, userInfo.getId()).eq(Message::getContactId, id))
                        .or(qw -> qw.eq(Message::getUserId, id).eq(Message::getContactId, userInfo.getId()))
                        .orderBy(true, false,Message::getCreateTime)
                        .page(new Page<>(1, 10));
            }
            case null -> {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }

        return page;
    }

    @Override
    public void sendToUser(Message message) {
        FriendMessage friendMessage = new FriendMessage();
        friendMessage.setFromUserId(message.getUserId());
        friendMessage.setToUserId(message.getContactId());
        friendMessage.setMessage(message.getMessage());
        rabbitTemplate.convertAndSend("chat.message.exchange", "chat.message.routing.key.friend", friendMessage);
    }

    @Override
    public void sendToGroup(Message message) {
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setFromUserId(message.getUserId());
        groupMessage.setGroupId(message.getContactId());
        QueryWrapper<GroupMember> groupMemberQueryWrapper = new QueryWrapper<>();
        groupMemberQueryWrapper.eq("groupId", message.getContactId());
        // 获取群成员
        List<GroupMember> groupMembers = groupMemberMapper.selectList(groupMemberQueryWrapper);
        // 获取群成员id
        groupMessage.setToUserIds(groupMembers.stream().map(GroupMember::getUserId).collect(Collectors.toList()));
        groupMessage.setMessage(message.getMessage());
        rabbitTemplate.convertAndSend("chat.group.message.exchange", "chat.message.routing.key.group", groupMessage);
    }
}
