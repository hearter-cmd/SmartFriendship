package com.yaonie.intelligent.assessment.server.chat_server.chat.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yaonie.intelligent.assessment.server.chat_server.chat.event.SendMessageEvent;
import com.yaonie.intelligent.assessment.server.chat_server.chat.mappers.MessageMapper;
import com.yaonie.intelligent.assessment.server.chat_server.chat.model.dto.MessageDto;
import com.yaonie.intelligent.assessment.server.chat_server.chat.service.MessageService;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.Message;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.UserService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.constant.UserConstant;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 21:27
 * @Description : TODO
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
    @Resource
    private UserService userService;
    @Resource
    private ApplicationEventPublisher publisher;
    @Resource
    private MessageMapper messageMapper;

    @Override
    public void sendMsg(MessageDto msg, HttpServletRequest request) {
        // 转换
        User loginUser = userService.getLoginUser(request);
        Message message = new Message();
        Long userId = loginUser.getId();
        BeanUtil.copyProperties(msg, message);
        message.setUserId(userId);
        // 消息推送
        publisher.publishEvent(new SendMessageEvent(this, message));
    }

    @Override
    public Page<Message> getMsgList(Long id, HttpServletRequest request) {
        User userInfo = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATE);
        LambdaQueryChainWrapper<Message> msgListWrapper = lambdaQuery()
                .or(qw -> qw.eq(Message::getUserId, userInfo.getId()).eq(Message::getContactId, id))
                .or(qw -> qw.eq(Message::getUserId, id).eq(Message::getContactId, userInfo.getId()));
        Page<Message> page = this.page(new Page<>(1, 10),
                msgListWrapper);
        return page;
    }
}
