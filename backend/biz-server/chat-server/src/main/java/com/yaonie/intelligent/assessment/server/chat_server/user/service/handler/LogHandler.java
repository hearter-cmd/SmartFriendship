package com.yaonie.intelligent.assessment.server.chat_server.user.service.handler;


import cn.hutool.json.JSONUtil;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 18:50
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName LogHandler
 * @Project backend
 * @Description : 
 */
@Component
public class LogHandler extends AbstractHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        log.info("\n接收到请求消息，内容：{}", JSONUtil.toJsonStr(wxMessage));
        return null;
    }
}
