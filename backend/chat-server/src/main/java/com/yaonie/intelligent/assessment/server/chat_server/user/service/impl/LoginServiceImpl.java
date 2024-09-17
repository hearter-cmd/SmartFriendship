package com.yaonie.intelligent.assessment.server.chat_server.user.service.impl;

import com.yaonie.intelligent.assessment.server.chat_server.common.enums.RedisKey;
import com.yaonie.intelligent.assessment.server.chat_server.config.ExecutorConfig;
import com.yaonie.intelligent.assessment.server.chat_server.user.service.LoginService;
import com.yaonie.intelligent.assessment.server.chat_server.utils.JwtUtil;
import com.yaonie.intelligent.assessment.server.common.util.RedisUtils;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-16 21:25
 * @Author 武春利
 * @CreateTime 2024-09-16
 * @ClassName LoginServiceImpl
 * @Project backend
 * @Description : 
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Value("${spring.session.timeout}")
    private static Long keyLiveTime;

    @Override
    @Async(ExecutorConfig.CHAT_EXECUTOR)
    public void renewalTokenIfNecessary(String token) {
        // TODO 判断token是否需要刷新
        Long uid = getValidUid(token);
        if (uid == null) {
            return;
        }
        Long expire = RedisUtils.getExpire(getUserTokenKey(uid));
        if (expire == -2) {
            // Key不存在
            return;
        }else if (expire < keyLiveTime * 0.8) {
            // 刷新token
            String newToken = JwtUtil.createToken(uid);
            RedisUtils.set(getUserTokenKey(uid), newToken, keyLiveTime, TimeUnit.SECONDS);
        }

    }

    // TODO 调用登录模块, 获取Token
    @Override
    public String login(Long uid) {
        String token = JwtUtil.createToken(uid);
        // 单位秒
        RedisUtils.set(getUserTokenKey(uid), token, 3, TimeUnit.DAYS);
        return token;
    }

    @Override
    public Long getValidUid(String token) {
        Long uid = JwtUtil.getUidOrNull(token);
        if (uid == null) {
            return null;
        }
        // 只要在期限内, 就能获取token, 但新旧在刷新策略下可能不一致
        String oldToken = RedisUtils.getStr(getUserTokenKey(uid));
        if (StringUtils.isBlank(oldToken)) {
            return null;
        }
        // 只有在新旧两个token相等的时候才进行返回, 否则就是刷新
        return Objects.equals(oldToken, token) ? uid : null;
    }

    private String getUserTokenKey(Long uid) {
        return RedisKey.getKey(RedisKey.USER_TOKEN_KEY, uid);
    }
}
