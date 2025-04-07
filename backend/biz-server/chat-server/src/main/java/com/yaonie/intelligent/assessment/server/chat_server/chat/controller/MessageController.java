package com.yaonie.intelligent.assessment.server.chat_server.chat.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yaonie.intelligent.assessment.server.chat_server.chat.model.dto.MessageDto;
import com.yaonie.intelligent.assessment.server.chat_server.chat.service.MessageService;
import com.yaonie.intelligent.assessment.server.common.model.common.BaseResponse;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.common.ResultUtils;
import com.yaonie.intelligent.assessment.server.common.model.exception.ThrowUtils;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.chat.Message;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-09-22 21:26
 * @Description : 聊天消息控制器
 */
@RestController
@RequestMapping("/chat/msg")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 发送消息
     *
     * @param msg     消息封装类
     * @param request 请求
     */
    @PostMapping("/send")
    public BaseResponse<Object> sendMsg(@RequestBody MessageDto msg, HttpServletRequest request) {
        ThrowUtils.throwIf(Objects.isNull(msg), ErrorCode.MESSAGE_IS_EMPTY);
        messageService.sendMsg(msg, request);
        return ResultUtils.success(null);
    }

    /**
     * 获取消息列表
     *
     * @param id 用户id
     * @return 消息列表
     */
    @GetMapping("/list/{id}")
    public BaseResponse<Page<Message>> getMsgList(@PathVariable("id") Long id,
                                                  @RequestParam("current") Integer current,
                                                  @RequestParam("size") Integer size,
                                                  HttpServletRequest request) {
        ThrowUtils.throwIf(Objects.isNull(id), ErrorCode.PARAMS_ERROR);
        return ResultUtils.success(messageService.getMsgList(id, current, size, request));
    }
}
