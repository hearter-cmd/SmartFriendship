package com.yaonie.intelligent.assessment.server.chat_server.user.service.handler;


import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 18:51
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName AbstractHandler
 * @Project backend
 * @Description :
 */
public abstract class AbstractHandler implements WxMpMessageHandler {
    protected Logger log = LoggerFactory.getLogger(getClass());
}
