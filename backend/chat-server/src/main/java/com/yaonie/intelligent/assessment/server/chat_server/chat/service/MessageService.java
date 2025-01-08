package com.yaonie.intelligent.assessment.server.chat_server.chat.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.server.chat_server.chat.model.dto.MessageDto;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.Message;
import jakarta.servlet.http.HttpServletRequest;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 21:27
 * @Description : 聊天消息服务
 */
public interface MessageService extends IService<Message> {
    /**
     * 发送消息
     * @param msg 消息内容
     * @param request 请求信息
     */
    void sendMsg(MessageDto msg, HttpServletRequest request);

    /**
     * 获取消息列表
     * @param id 用户id
     * @param request 请求信息
     * @return 消息列表
     */
    Page<Message> getMsgList(Long id, HttpServletRequest request);

    /**
     * 发送消息给用户
     * @param message 消息内容
     */
    void sendToUser(Message message);

    /**
     * 发送消息给群组
     * @param message 消息内容
     */
    void sendToGroup(Message message);
}
