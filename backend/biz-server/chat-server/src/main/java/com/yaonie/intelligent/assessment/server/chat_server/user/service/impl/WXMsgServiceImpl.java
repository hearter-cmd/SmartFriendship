package com.yaonie.intelligent.assessment.server.chat_server.user.service.impl;


import com.yaonie.intelligent.assessment.server.chat_server.user.adapter.TextBuilder;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.WXMsgService;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.adepter.UserAdepter;
import com.yaonie.intelligent.assessment.server.chat_server.websocket.service.WebSocketService;
import com.yaonie.intelligent.assessment.server.common.model.common.ErrorCode;
import com.yaonie.intelligent.assessment.server.common.model.exception.BusinessException;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import com.yaonie.intelligent.assessment.system.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-16 16:29
 * @Author 武春利
 * @CreateTime 2024-09-16
 * @ClassName WXMsgServiceImpl
 * @Project backend
 * @Description : 
 */
@Service
@Slf4j
public class WXMsgServiceImpl implements WXMsgService {
    // OpenId和登录事件的的关系
    private static final ConcurrentHashMap<String, Integer> WAIT_AUTHORIZE_MAP = new ConcurrentHashMap<>();

    @Resource
    private WebSocketService webSocketService;
    @Value("${wx.mp.callback}")
    private String callback;
    @Resource
    @Lazy
    private WxMpService wxMpService;
    @Resource
    private UserService userService;

    /**
     * 微信扫描二维码事件
     * @param wxMessage Wx消息封装
     * @return 发送给用户的授权消息
     */
    @Override
    public WxMpXmlOutTextMessage scan(WxMpXmlMessage wxMessage) {
        WxMpXmlOutTextMessage out;
        // 来自于哪个用户 openId
        String fromUserOpenId = wxMessage.getFromUser();
        // 扫描带参数二维码事件 /* qrscene_开头的 扫描二维码事件 */
        // 在逻辑下, 扫描带参二维码==登录
        Integer code = getEventKey(wxMessage);
        if (code == null) {
            return null;
        }
        // 判断当前用户是否已经存储过登录信息了
        User userInfo = userService.getUserByMpOpenId(fromUserOpenId);
        // 如果已经存储过登录信息了，则直接返回, 如果名字为空则证明它没有授权, 也就没有登录
        if (Objects.nonNull(userInfo) && StringUtils.isNoneBlank(userInfo.getUserName())) {
            webSocketService.scanLoginSuccess(code, userInfo.getId());
            return TextBuilder.build("登录成功!", wxMessage);
        }
        //user为空先注册,手动生成,以保存openId
        if (Objects.isNull(userInfo)) {
            // 扫码直接注册, 等待用户授权信息
            User user = UserAdepter.buildUserSave(fromUserOpenId);
            userService.save(user);
        }
        WAIT_AUTHORIZE_MAP.put(fromUserOpenId, code);
        String getUserInfoUrl = wxMpService.getOAuth2Service().buildAuthorizationUrl(callback+"/api/wx/mp/callback", "snsapi_base", "STATE");
        // 在这里并没有真正的发送, 必须return才行
        out = TextBuilder.build(String.format("欢迎关注，点击<a href=\"%s\">这里</a>开始使用", getUserInfoUrl), wxMessage);
        return out;
    }

    @Override
    public void authorize(WxOAuth2UserInfo userInfo) {
        // 查找用户信息
        String openid = userInfo.getOpenid();
        User user = userService.getUserByMpOpenId(openid);
        if (Objects.isNull(user)) {
            // 新用户但是没按照步骤走，存在问题
            throw new BusinessException(ErrorCode.FEIGN_REQUEST);
        } else if (StringUtils.isBlank(user.getUserAvatar())){
            // 更新用户信息
            User newUser = UserAdepter.buildAuthorizeUser(userInfo);
            newUser.setId(user.getId());
            userService.updateById(newUser);
        }
        // 在等待队列中移除当前用户
        Integer code = WAIT_AUTHORIZE_MAP.remove(openid);
        // 用户已注册
        webSocketService.scanLoginSuccess(code, user.getId());
    }

    private Integer getEventKey(WxMpXmlMessage wxMessage) {
        String eventKey = wxMessage.getEventKey();
        try {
            String code;
            if (eventKey.startsWith("qrscene_")) {
                code = eventKey.substring(8);
            } else {
                code = eventKey;
            }
            return Integer.valueOf(code);
        } catch (Exception e) {
            log.error("getEventKey error: {}", eventKey, e);
            return null;
        }
    }
}
