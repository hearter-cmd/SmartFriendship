package com.yaonie.intelligent.assessment.server.chat_server.utils;


import cn.hutool.extra.spring.SpringUtil;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisSessionRepository;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-17 21:52
 * @Author 武春利
 * @CreateTime 2024-09-17
 * @ClassName SessionUtil
 * @Project backend
 * @Description : TODO
 */
@Slf4j
public class SessionUtil {
    private static final RedisSessionRepository SESSION_REPOSITORY;

    static {
        SESSION_REPOSITORY = SpringUtil.getBean(RedisSessionRepository.class);
    }

    public static Session findSessionByHeaders(HttpHeaders headers) {
        String cookies = null;
        for (Map.Entry<String, String> header : headers) {
            if ("Cookie".equals(header.getKey())) {
                cookies = header.getValue();
                log.info("cookies:{}", cookies);
                if (StringUtils.isBlank(cookies)) {
                    return null;
                } else {
                    break;
                }
            }
        }
        String[] cookieArr = cookies.split(",");
        Session session = null;
        for (String cookie : cookieArr) {
            String[] kv = cookie.split("=");
            if ("SESSION".equals(kv[0])) {
                byte[] decodedBytes = Base64.getDecoder().decode(kv[1]);
                session = SESSION_REPOSITORY.findById(new String(decodedBytes));
                if (!Objects.isNull(session)) {
                    return session;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public static Session findSessionBySessionId(String sessionId) {
        byte[] decodedBytes = Base64.getDecoder().decode(sessionId);
        Session session = SESSION_REPOSITORY.findById(new String(decodedBytes));
        if (!Objects.isNull(session)) {
            return session;
        } else {
            return null;
        }
    }
}
