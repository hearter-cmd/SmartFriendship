package com.yaonie.intelligent.assessment.system.wxmp;

import com.yaonie.intelligent.assessment.system.wxmp.handler.EventHandler;
import com.yaonie.intelligent.assessment.system.wxmp.handler.MessageHandler;
import com.yaonie.intelligent.assessment.system.wxmp.handler.SubscribeHandler;
import jakarta.annotation.Resource;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.context.annotation.Bean;

/**
 * 微信公众号路由
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
//@Configuration
public class WxMpMsgRouter {

    @Resource
    private WxMpService wxMpService;

    @Resource
    private EventHandler eventHandler;

    @Resource
    private MessageHandler messageHandler;

    @Resource
    private SubscribeHandler subscribeHandler;

    @Bean
    public WxMpMessageRouter getWxMsgRouter() {
        WxMpMessageRouter router = new WxMpMessageRouter(wxMpService);
        // 消息
        router.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.TEXT)
                .handler(messageHandler)
                .end();
        // 关注
        router.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(subscribeHandler)
                .end();
        // 点击按钮
        router.rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.CLICK)
                .eventKey(WxMpConstant.CLICK_MENU_KEY)
                .handler(eventHandler)
                .end();
        return router;
    }
}
