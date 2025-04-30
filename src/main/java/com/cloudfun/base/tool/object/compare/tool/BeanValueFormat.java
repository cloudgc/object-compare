package com.cloudfun.base.tool.object.compare.tool;

import com.cloudfun.base.tool.object.compare.anno.Name;
import com.cloudfun.base.tool.object.compare.bean.BeanFieldDetail;
import com.cloudfun.base.tool.object.compare.exception.CompareException;
import com.cloudfun.base.tool.object.compare.option.CompareOption;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 17:05
 */
public interface BeanValueFormat {


    /**
     * format value to string
     *
     * <pre>
     *   if  @Name.formatType() has value use this format first
     *      or  use  option config
     *   final use Object.toString()
     * </pre>
     *
     * @param value           bean field value
     * @param beanFieldDetail parse bean field meta info
     * @param option          format config
     * @return format value result
     */
    static String primitiveFormat(Object value, BeanFieldDetail beanFieldDetail, CompareOption option) {

        if (value == null) {
            return null;
        }

        Name nameAnno = beanFieldDetail.getFieldProperty().getAnnotation(Name.class);
        if (nameAnno != null && !nameAnno.formatType().equals(BeanValueFormat.class)) {
            // use  format type to format
            try {
                BeanValueFormat beanValueFormat = (BeanValueFormat) nameAnno.formatType().getDeclaredConstructor().newInstance();
                return beanValueFormat.format(value, beanFieldDetail, option);
            } catch (Exception e) {
                throw new CompareException(e);
            }
        }

        if (option.getTypeBeanValueFormat() != null && option.getTypeBeanValueFormat().containsKey(value.getClass())) {
            return option.getTypeBeanValueFormat().get(value.getClass()).format(value, beanFieldDetail, option);
        }


        Field fieldProperty = beanFieldDetail.getFieldProperty();
        if (fieldProperty.getType().getName().equals("double") || fieldProperty.getType().getName().equals("float") || value instanceof Double || value instanceof Float) {
            return option.getNumberFormat().format(value);
        }
        if (beanFieldDetail.getFieldProperty().getType().isPrimitive() || value instanceof String) {
            return value.toString();
        }

        return switch (value) {
            case LocalDateTime ignored -> option.getDateTimeFormat().format((TemporalAccessor) value);
            case LocalDate ignored -> option.getDateFormat().format((TemporalAccessor) value);
            case LocalTime ignored -> option.getTimeFormat().format((TemporalAccessor) value);
            case Date ignored ->
                    option.getDateFormat().format(((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            case BigDecimal ignored -> option.getNumberFormat().format(value);
            default -> value.toString();
        };


    }


    String format(Object value, BeanFieldDetail beanFieldDetail, CompareOption option);


}
