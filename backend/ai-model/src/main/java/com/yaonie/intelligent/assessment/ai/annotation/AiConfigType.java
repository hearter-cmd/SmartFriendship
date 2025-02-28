package com.yaonie.intelligent.assessment.ai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 *
 * </p>
 *
 * @author 武春利
 * @since 2025-01-08
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AiConfigType {
    String type();
}
