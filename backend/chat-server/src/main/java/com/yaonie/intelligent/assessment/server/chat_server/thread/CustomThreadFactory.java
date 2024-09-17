package com.yaonie.intelligent.assessment.server.chat_server.thread;


import lombok.AllArgsConstructor;

import java.util.concurrent.ThreadFactory;

/**
 * _*_ coding : utf-8 _*_
 *
 * @Date 2024-09-17 12:51
 * @Author 武春利
 * @CreateTime 2024-09-17
 * @ClassName CustomThreadFactory
 * @Project backend
 * @Description : TODO
 */
@AllArgsConstructor
public class CustomThreadFactory implements ThreadFactory {
    private static final CustomUncaughtExceptionHandler MY_UNCAUGHT_EXCEPTION_HANDLER = new CustomUncaughtExceptionHandler();
    private ThreadFactory original;
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = original.newThread(r);
        thread.setUncaughtExceptionHandler(MY_UNCAUGHT_EXCEPTION_HANDLER);
        return null;
    }
}
