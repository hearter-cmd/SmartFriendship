package com.yaonie.intelligent.assessment.server.chat_server.user.service.handler;


import com.yaonie.intelligent.assessment.server.chat_server.user.adapter.TextBuilder;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.WXMsgService;
import jakarta.annotation.Resource;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 18:50
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName SubscribeHandler
 * @Project backend
 * @Description :
 */
@Component
public class SubscribeHandler extends AbstractHandler {
    @Resource
    private WXMsgService wxMsgService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        WxMpXmlOutTextMessage scan;
        try {
            scan = wxMsgService.scan(wxMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (scan != null) {
            return scan;
        }
        return TextBuilder.build("欢迎关注我哦~~~", wxMessage);
    }
}
