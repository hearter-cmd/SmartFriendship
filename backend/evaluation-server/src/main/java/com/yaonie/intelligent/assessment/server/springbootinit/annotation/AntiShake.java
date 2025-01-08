package com.yaonie.intelligent.assessment.server.springbootinit.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  防抖接口
 * </p>
 *
 * @author 武春利
 * @since 2025-01-01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AntiShake {
    /*
     * 存活时间
     */
    int surviveTime();

    /*
     * 时间单位
     */
    TimeUnit timeUnit();
}
