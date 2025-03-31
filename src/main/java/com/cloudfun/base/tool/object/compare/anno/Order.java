package com.cloudfun.base.tool.object.compare.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 14:55
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Order {
    /**
     * order by value asc
     */
    int value() default 0;
}
