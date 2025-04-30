package com.cloudfun.base.tool.object.compare.anno;

import com.cloudfun.base.tool.object.compare.tool.BeanValueFormat;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/28 16:29
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

    String value() default "";

    Class<?> formatType() default BeanValueFormat.class;

}
