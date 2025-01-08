package com.yaonie.intelligent.assessment.server.chat_server.common.constants;


/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-17 2:28
 * @Author 武春利
 * @CreateTime 2024-09-17
 * @ClassName RedisKeyEnum
 * @Project backend
 * @Description : RedisKey枚举
 */
public class RedisKey {
    private static final String BASE_KEY = "yaonie:chat:";
    public static final String USER_TOKEN_KEY = "user:token:%d";

    public static String getKey(String key, Long userId) {
        return BASE_KEY + String.format(key, userId);
    }
}
