package com.yaonie.intelligent.assessment.server.springbootinit.likepart;


import com.yaonie.intelligent.assessment.server.common.model.model.dto.appLike.AppGiveLikeAllDto;
import org.springframework.stereotype.Component;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Author 武春利
 * @CreateTime 2024-10-14 20:02
 * @Description : 点赞监听
 */
@Component
public class GiveLikeRabbitListener {
//    @RabbitListener(bindings = {
//            @QueueBinding(
//                    value = @Queue(name = "give.like.queue"),
//                    exchange = @Exchange(name = "give.like.exchange"),
//                    key = "give.like.routing.key",
//                    declare = "true"
//            )
//    })
    public void giveLike(AppGiveLikeAllDto appGiveLikeAllDto) {

    }
}
