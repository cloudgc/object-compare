package com.cloudfun.base.tool.object.compare.tool;

import com.cloudfun.base.tool.object.compare.bean.BeanFieldAnnotation;
import com.cloudfun.base.tool.object.compare.bean.BeanFieldDetail;
import com.cloudfun.base.tool.object.compare.bean.CompareResult;
import com.cloudfun.base.tool.object.compare.contants.CompareType;
import com.cloudfun.base.tool.object.compare.exception.CompareException;
import com.cloudfun.base.tool.object.compare.option.CompareOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 14:42
 */

public class BeanCompare {

    private static final Logger log = LoggerFactory.getLogger(BeanCompare.class);


    /**
     * simple java bean compare
     *
     * @param origin origin java bean
     * @param target target java bean
     * @param option compare option like format or config flags
     * @return compare result
     */
    public List<CompareResult> compare(Object origin, Object target, CompareOption option) {

        if (origin == null && target == null) {
            return Collections.emptyList();
        }
        if (origin != null && target != null && !origin.getClass().equals(target.getClass())) {
            throw new CompareException("origin and target must be same type");
        }

        // get bean field info List
        BeanFieldAnnotation fieldList = BeanInfoTool.getFieldList(origin, target, option);


        List<BeanFieldDetail> nameFieldList = fieldList.getNameFieldList();

        List<CompareResult> compareResultList = new ArrayList<>();

        for (BeanFieldDetail beanFieldDetail : nameFieldList) {
            CompareResult result = new CompareResult();
            result.setField(beanFieldDetail.getField());
            result.setFieldName(beanFieldDetail.getFieldName());


            Class<?> fieldType = beanFieldDetail.getFieldProperty().getType();
            Method readMethod = beanFieldDetail.getPropertyDescriptor().getReadMethod();

            Object originValue = getFieldValue(readMethod, origin);
            Object targetValue = getFieldValue(readMethod, origin);

            if (BeanInfoTool.isPrimitive(fieldType)) {
                // primitive type compare
                CompareType compareValue = getCompareValueType(readMethod, targetValue);
                result.setCompareType(compareValue);
                result.setOriginValue(BeanValueFormat.primitiveFormat(originValue, beanFieldDetail, option));
                result.setOriginValue(BeanValueFormat.primitiveFormat(targetValue, beanFieldDetail, option));

            } else {
                // ref bean type compare
                List<CompareResult> compare = compare(originValue, targetValue, option);
                result.setChildren(compare);
                CompareType compareValue = getCompareTypeByResultList(compare, originValue, targetValue);
                result.setCompareType(compareValue);
                result.setChildren(compare);
            }
            compareResultList.add(result);
        }

        return compareResultList;


    }

    private CompareType getCompareTypeByResultList(List<CompareResult> compare, Object originValue, Object targetValue) {
        if (compare == null || compare.isEmpty()) {
            return CompareType.NONE;
        }
        if (originValue == null && targetValue == null) {
            return CompareType.NONE;
        }
        if (originValue == null) {
            return CompareType.ADD;
        }
        if (targetValue == null) {
            return CompareType.DELETE;
        }

        for (CompareResult result : compare) {
            if (CompareType.NONE.equals(result.getCompareType())) {
                return CompareType.CHANGE;
            }
        }

        return CompareType.NONE;
    }

    @SuppressWarnings("unchecked")
    private CompareType getCompareValueType(Object originValue, Object targetValue) {
        if (originValue == null && targetValue == null) {
            return CompareType.NONE;
        }
        if (originValue == null) {
            return CompareType.ADD;
        } else if (targetValue == null) {
            return CompareType.DELETE;
        }

        Class<?> beanClass = BeanInfoTool.getBeanClass(originValue, targetValue);
        if (Comparable.class.isAssignableFrom(beanClass)) {
            boolean compareResult = ((Comparable) originValue).compareTo((Comparable) targetValue) != 0;
            if (compareResult) {
                return CompareType.CHANGE;
            }
        }
        if (originValue.equals(targetValue)) {
            return CompareType.NONE;
        } else {
            return CompareType.CHANGE;
        }


    }


    private Object getFieldValue(Method method, Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return method.invoke(obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


}
