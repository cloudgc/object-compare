package com.cloudfun.base.tool.object.compare.tool;

import com.cloudfun.base.tool.object.compare.bean.BeanFieldDetail;
import com.cloudfun.base.tool.object.compare.option.CompareOption;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 17:05
 */
public interface BeanValueFormat {



    static String primitiveFormat(Object value, BeanFieldDetail beanFieldDetail, CompareOption option) {

        if (value == null) {
            return null;
        }
        Field fieldProperty = beanFieldDetail.getFieldProperty();
        if (fieldProperty.getType().getName().equals("double")
                || fieldProperty.getType().getName().equals("float")
                || value instanceof Double || value instanceof Float) {
            return option.getNumberFormat().format(value);
        }
        if (beanFieldDetail.getFieldProperty().getType().isPrimitive() || value instanceof String) {
            return value.toString();
        }

        if (value instanceof TemporalAccessor) {
            return option.getDateTimeFormat().format((TemporalAccessor) value);
        }











        return value.toString();
    }


    String format(Object value, BeanFieldDetail beanFieldDetail, CompareOption option);


}
