package com.yaonie.intelligent.assessment.server.chat_server.common.config;


import com.yaonie.intelligent.assessment.server.chat_server.user.service.handler.LogHandler;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.handler.MsgHandler;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.handler.ScanHandler;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.handler.SubscribeHandler;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-15 18:14
 * @Author 武春利
 * @CreateTime 2024-09-15
 * @ClassName WxMpConfiguration
 * @Project backend
 * @Description :
 */
@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(WxMpProperties.class)
public class WxMpConfiguration {
    private MsgHandler msgHandler;
    private LogHandler logHandler;
    private ScanHandler scanHandler;
    private SubscribeHandler subscribeHandler;
    private WxMpProperties wxMpProperties;


    @Bean
    public WxMpService wxMpService() {
        WxMpService wxMpService = new WxMpServiceImpl();
        // 设置配置文件
        Map<String, WxMpConfigStorage> map = wxMpProperties
                .getConfigs()
                .stream()
                // 将参数MpConfig转换为WxMpDefaultConfigImpl
                .map(item -> {
                    WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
                    wxMpDefaultConfig.setAppId(item.getAppId());
                    wxMpDefaultConfig.setSecret(item.getSecret());
                    wxMpDefaultConfig.setToken(item.getToken());
                    wxMpDefaultConfig.setAesKey(item.getAesKey());
                    return wxMpDefaultConfig;
                })
                // 以APPID作为键, 同一存储不同的公众号配置
                .collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, config -> config, (o, e) -> o));
        wxMpService.setMultiConfigStorages(map);
        return wxMpService;
    }

    @Bean
    public WxMpMessageRouter messageRouter(WxMpService wxMpService) {
        WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(wxMpService);
        // 记录日志
        wxMpMessageRouter.rule().handler(logHandler).next();

        // 关注事件
        wxMpMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SUBSCRIBE).handler(subscribeHandler).end();

        // 扫描带参数二维码事件
        wxMpMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT).event(WxConsts.EventType.SCAN).handler(scanHandler).end();

        // 默认事件
        wxMpMessageRouter.rule().async(false).handler(msgHandler).end();

        return wxMpMessageRouter;
    }

}
