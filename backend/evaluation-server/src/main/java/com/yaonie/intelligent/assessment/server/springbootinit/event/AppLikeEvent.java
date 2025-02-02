package com.yaonie.intelligent.assessment.server.springbootinit.event;


import com.yaonie.intelligent.assessment.server.common.model.model.dto.appLike.AppGiveLikeAllDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-10-13 18:52
 * @Description : App点赞事件
 */
@Getter
public class AppLikeEvent extends ApplicationEvent {
    private final AppGiveLikeAllDto appGiveLikeAllDto;

    public AppLikeEvent(AppGiveLikeAllDto appGiveLikeAllDto) {
        super(appGiveLikeAllDto);
        this.appGiveLikeAllDto = appGiveLikeAllDto;
    }
}
