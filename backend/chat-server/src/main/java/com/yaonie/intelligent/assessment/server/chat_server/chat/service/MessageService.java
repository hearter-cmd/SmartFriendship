package com.yaonie.intelligent.assessment.server.chat_server.chat.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yaonie.intelligent.assessment.server.chat_server.chat.model.dto.MessageDto;
import com.yaonie.intelligent.assessment.server.chat_server.common.model.entity.Message;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 21:27
 * @Description : TODO
 */
public interface MessageService extends IService<Message> {
    void sendMsg(MessageDto msg, HttpServletRequest request);

    Page<Message> getMsgList(Long id, HttpServletRequest request);
}
