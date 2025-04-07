package com.yaonie.intelligent.assessment.server.chat_server.user.controller;


import com.yaonie.intelligent.assessment.server.chat_server.user.service.WXMsgService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 17:39
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WxMpController
 * @Project backend
 * @Description :
 */
@RestController
@RequestMapping("/wx/mp")
@Slf4j
public class WxPortalController {
    @Resource
    private WxMpService wxMpService;
    @Resource
    private WxMpMessageRouter messageRouter;
    @Resource
    private WXMsgService wxMsgService;


    @RequestMapping("/test")
    public String test(@RequestParam Integer id) throws WxErrorException {
        System.out.println(wxMpService);
        WxMpQrCodeTicket wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(id, 60);
        return wxMpQrCodeTicket.getUrl();
    }

    @PostMapping(value = "/", produces = "application/xml; charset=UTF-8")
    public String index(
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam("openid") String openid,
            @RequestParam(name = "encrypt_type", required = false) String encType,
            @RequestParam(name = "msg_signature", required = false) String msgSignature
    ) {
        log.info("接收到微信请求：[openid={}, signature={}, timestamp={}, nonce={}, encType={}, msgSignature={}]", openid, signature, timestamp, nonce, encType, msgSignature);
        if (!wxMpService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        String out = null;
        if (encType == null) {
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }
            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encType)) {
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody,
                    wxMpService.getWxMpConfigStorage(), timestamp, nonce, msgSignature);
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return null;
            }
            out = outMessage.toEncryptedXml(wxMpService.getWxMpConfigStorage());
        }

        log.debug("\n组装回复信息：{}", out);
        return out;
    }

    /**
     * 微信接入验证
     *
     * @param timestamp 时间戳
     * @param nonce     随机数
     * @param signature 签名
     * @param echostr   随机字符串
     * @return 验证字符串
     */
    @GetMapping("/")
    public String check(String timestamp, String nonce, String signature, String echostr) {
        log.info("check");
        if (StringUtils.isAnyBlank(timestamp, nonce, signature, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        // 使用WXJAVA自带的校验工具进行校验签名
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        } else {
            return "";
        }
    }

    @GetMapping("/callback")
    public RedirectView callBack(@RequestParam String code) throws WxErrorException {
        WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
        WxOAuth2UserInfo zhCn = wxMpService.getOAuth2Service().getUserInfo(accessToken, "zh_CN");
        // 授权用户
        wxMsgService.authorize(zhCn);
        log.info(zhCn.toString());
        RedirectView redirectView = new RedirectView("http://1.94.141.32/");
        return redirectView;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }
        return null;
    }
}
