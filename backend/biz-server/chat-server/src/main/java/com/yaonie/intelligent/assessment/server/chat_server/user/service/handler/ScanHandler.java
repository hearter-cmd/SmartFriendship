package com.yaonie.intelligent.assessment.server.chat_server.user.service.handler;


import com.yaonie.intelligent.assessment.server.chat_server.user.service.WXMsgService;
import jakarta.annotation.Resource;
import me.chanjar.weixin.common.error.WxErrorException;
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
 * @ClassName ScanHandler
 * @Project backend
 * @Description : 
 */
@Component
public class ScanHandler extends AbstractHandler {
    @Resource
    private WXMsgService wxMsgService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        // 扫描二维码执行的部分
        // TODO 扫码事件, 扫码成功, 请求用户信息权限, 并回调给当前服务器进行解析
        return wxMsgService.scan(wxMessage);
    }
}
