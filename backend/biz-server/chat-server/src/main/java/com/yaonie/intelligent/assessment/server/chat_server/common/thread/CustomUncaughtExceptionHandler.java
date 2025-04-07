package com.yaonie.intelligent.assessment.server.chat_server.common.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-17 12:44
 * @Author 武春利
 * @CreateTime 2024-09-17
 * @ClassName UncaughtExceptionHandler
 * @Project backend
 * @Description : 线程异常处理类
 */
@Slf4j
public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("UncaughtException in thread {} : {}", t.getName(), e.getMessage(), e);
    }
}
