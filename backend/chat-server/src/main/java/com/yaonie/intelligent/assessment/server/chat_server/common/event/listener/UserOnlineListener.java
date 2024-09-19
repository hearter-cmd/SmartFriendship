package com.yaonie.intelligent.assessment.server.chat_server.common.event.listener;


import com.yaonie.intelligent.assessment.server.chat_server.common.event.UserOnlineEvent;
import com.yaonie.intelligent.assessment.server.chat_server.user.mappers.UserMapper;
import com.yaonie.intelligent.assessment.server.common.model.model.entity.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-18 17:06
 * @Author 武春利
 * @CreateTime 2024-09-18
 * @ClassName UserRegisterListener
 * @Project backend
 * @Description : TODO
 */
@Slf4j
@Component
public class UserOnlineListener {
    @Resource
    private UserMapper userMapper;

    // 使用`@EventListener`进行事件监控
    // 它可以使得一个代码块中的方法, 不使用同一个事务, 也可以进行异步
    // 异步只需要添加一个`@Async`
    @Async
    @EventListener(classes = UserOnlineEvent.class)
    public void saveDb(UserOnlineEvent userOnlineEvent) {
        // TODO:
        log.info("用户上线事件监听器");
        User user = userOnlineEvent.getUser();
        if (Objects.isNull(user)) {
            return;
        }
        User update = new User();
        update.setAreaName(user.getAreaName());
        update.setAreaCode(user.getAreaCode());
        update.setLastLoginTime(user.getLastLoginTime());
        update.setIp(user.getIp());
        // 1. 保存用户信息到数据库
        userMapper.updateById(update, user.getId());
        // 2. 通知其他用户
    }

//    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
}
