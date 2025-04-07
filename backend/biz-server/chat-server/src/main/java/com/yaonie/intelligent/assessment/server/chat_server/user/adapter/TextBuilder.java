package com.yaonie.intelligent.assessment.server.chat_server.user.adapter;


import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 20:45
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName TextBuidler
 * @Project backend
 * @Description :
 */
public class TextBuilder {
    public static WxMpXmlOutTextMessage build(String text, WxMpXmlMessage message) {
        WxMpXmlOutTextMessage out = WxMpXmlOutMessage.TEXT().content(text)
                .fromUser(message.getToUser())
                .toUser(message.getFromUser())
                .build();
        return out;
    }
}
