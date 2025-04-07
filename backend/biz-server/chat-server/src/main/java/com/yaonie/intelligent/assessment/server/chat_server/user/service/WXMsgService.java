package com.yaonie.intelligent.assessment.server.chat_server.user.service;


import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-16 16:29
 * @Author 武春利
 * @CreateTime 2024-09-16
 * @ClassName WXMsgService
 * @Project backend
 * @Description :
 */
public interface WXMsgService {
    /**
     * 用户扫码成功进行的事件
     *
     * @param wxMessage Wx消息封装
     */
    WxMpXmlOutTextMessage scan(WxMpXmlMessage wxMessage);

    /**
     * 用户授权成功的事件
     *
     * @param zhCn 用户信息
     */
    void authorize(WxOAuth2UserInfo zhCn);
}
