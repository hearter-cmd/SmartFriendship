package com.yaonie.intelligent.assessment.server.chat_server.websocket.utils;


import cn.hutool.extra.spring.SpringUtil;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisSessionRepository;

import java.util.Base64;
import java.util.Objects;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-17 21:52
 * @Author 武春利
 * @CreateTime 2024-09-17
 * @ClassName SessionUtil
 * @Project backend
 * @Description : Session工具类
 */
@Slf4j
public class SessionUtil {

    private static final SessionRepository SESSION_REPOSITORY;

    static {
        SESSION_REPOSITORY = SpringUtil.getBean(RedisSessionRepository.class);
    }

    /**
     * 通过请求头获取session
     * @param headers Headers
     * @return Session
     */
    public static Session findSessionByHeaders(HttpHeaders headers) {
        String sessionId = getSessionIdByHeaders(headers);
        if (StringUtils.isBlank(sessionId)) {
            return null;
        }
        return findSessionBySessionId(sessionId);
    }

    /**
     * 通过sessionId获取session
     * @param sessionId sessionId
     * @return Session
     */
    public static Session findSessionBySessionId(String sessionId) {
        byte[] decodedBytes = Base64.getDecoder().decode(sessionId);
        Session session = SESSION_REPOSITORY.findById(new String(decodedBytes));
        if (!Objects.isNull(session)) {
            return session;
        } else {
            return null;
        }
    }

    /**
     * 从请求头中获取sessionId
     * @param headers Headers
     * @return sessionId
     */
    public static String getSessionIdByHeaders(HttpHeaders headers) {
        String cookies = headers.get("Cookie");;
        if (StringUtils.isBlank(cookies)) {
            return null;
        }
        String[] cookieArr = cookies.split(";");
        for (String cookie : cookieArr) {
            String[] kv = cookie.split("=");
            if ("SESSION".equals(kv[0]) && !kv[1].startsWith("[")) {
                return kv[1];
            }
        }
        return null;
    }

    /**
     * 创建session并返回sessionId
     * @return sessionId
     */
    public static String createSessionId() {
        RedisSessionRepository sessionRepository = (RedisSessionRepository) SESSION_REPOSITORY;
        Session session = sessionRepository.createSession();
        String id = session.getId();
        SESSION_REPOSITORY.save(session);
        byte[] encodedBytes = Base64.getEncoder().encode(id.getBytes());
        return new String(encodedBytes);
    }

    public static void saveSession(Session session) {
        SESSION_REPOSITORY.save(session);
    }

    public static void removeSessionById(String sessionId) {
        SESSION_REPOSITORY.deleteById(sessionId);
    }
}
