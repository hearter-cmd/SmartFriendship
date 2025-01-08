package com.yaonie.intelligent.assessment.server.springbootinit.event.listener;


import com.yaonie.intelligent.assessment.server.common.model.model.dto.appLike.AppGiveLikeAllDto;
import com.yaonie.intelligent.assessment.server.springbootinit.event.AppLikeEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-10-13 18:53
 * @Description : TODO
 */
public class AppLikeEventListener {
//    @Resource
//    private RabbitTemplate rabbitTemplate;

    @Async
    @EventListener(classes = {AppLikeEvent.class})
    public void onApplicationEvent(AppLikeEvent appLikeEvent) {
        AppGiveLikeAllDto appGiveLikeDto = appLikeEvent.getAppGiveLikeAllDto();
//        rabbitTemplate.convertAndSend("app_like", JSONUtil.toJsonStr(appGiveLikeDto));
    }
}
