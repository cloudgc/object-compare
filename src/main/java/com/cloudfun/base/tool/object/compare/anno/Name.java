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

    /**
     * for  CompareResult.fieldName
     */
    String value() default "";

    /**
     * set CompareResult.originValue/targetValue format
     */
    Class<?> formatType() default BeanValueFormat.class;


    /**
     * array field compare by sort value or not
     *
     * <pre>
     *      private int [] array;
     *      origin array = {1,2,3}
     *      target array = {3,2,1}
     *
     *      if ignoreArraySort = false:
     *          CompareResult = change
     *
     *      if ignoreArraySort = true:
     *          CompareResult = none
     * </pre>
     */
    boolean ignoreArraySort() default false;

}
