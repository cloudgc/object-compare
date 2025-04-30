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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
            Object targetValue = getFieldValue(readMethod, target);

            if (BeanInfoTool.isPrimitive(fieldType)) {
                // primitive type compare
                CompareType compareValue = getCompareValueType(originValue, targetValue);
                result.setCompareType(compareValue);
                result.setOriginValue(BeanValueFormat.primitiveFormat(originValue, beanFieldDetail, option));
                result.setTargetValue(BeanValueFormat.primitiveFormat(targetValue, beanFieldDetail, option));
            } else {

                List<CompareResult> compareChildBean;
                if (BeanInfoTool.isCollectionType(fieldType)) {
                    // Collection  compare
                    compareChildBean = compareCollection(originValue, targetValue, option);
                    CompareType compareValue = getCompareTypeByResultList(compareChildBean, originValue, targetValue);
                    result.setCompareType(compareValue);
                    result.setCollection(true);

                } else {
                    // ref bean type compare
                    compareChildBean = compare(originValue, targetValue, option);
                    CompareType compareValue = getCompareTypeByResultList(compareChildBean, originValue, targetValue);
                    result.setCompareType(compareValue);
                }
                result.setChildren(compareChildBean);


            }
            compareResultList.add(result);
        }

        return compareResultList;


    }

    /**
     * compare collection
     *
     * <p>
     * support map list set
     *
     * @param origin
     * @param target
     * @param option
     * @return
     */

    public List<CompareResult> compareCollection(Object origin, Object target, CompareOption option) {
        if (origin == null && target == null) {
            return Collections.emptyList();
        }

        if (origin != null && target != null && !origin.getClass().equals(target.getClass())) {
            throw new CompareException("origin and target must be same type");
        }

        Class<?> beanClass = BeanInfoTool.getBeanClass(origin, target);
        if (BeanInfoTool.isCollectionType(beanClass)) {
            return Collections.emptyList();
        }

        if (beanClass.isArray()) {
            return compareArray(origin, target, option);
        }

        if (Map.class.isAssignableFrom(beanClass)) {
            return compareMap(origin, target, option);
        }


        return compareBaseCollection((Collection<?>) origin, (Collection<?>) target, option);
    }


    private List<CompareResult> compareBaseCollection(Collection<?> origin, Collection<?> target,
                                                      CompareOption option) {

        Class<?> beanClass = BeanInfoTool.getCollectionRawClass(origin, target);
        if (Object.class.equals(beanClass)) {
            return Collections.emptyList();
        }

        if (origin == null) {
            origin = Collections.emptyList();
        }
        if (target == null) {
            target = Collections.emptyList();
        }

        List<CompareResult> compareResultList = new ArrayList<>();
        //   primitive type collection
        if (BeanInfoTool.isPrimitive(beanClass)) {

            int maxSize = Math.max(origin.size(), target.size());


            Iterator<?> originIterator = origin.iterator();
            Iterator<?> targetIterator = target.iterator();

            for (int i = 0; i < maxSize; i++) {
                Object originElement = originIterator.hasNext() ? originIterator.next() : null;
                Object targetElement = targetIterator.hasNext() ? targetIterator.next() : null;

                CompareResult result = new CompareResult();
                result.setField(Integer.toString(i));
                result.setFieldName(result.getField());
                result.setCompareType(getCompareValueType(originElement, targetElement));

                result.setOriginValue(BeanValueFormat.primitiveFormat(originElement, option));
                result.setTargetValue(BeanValueFormat.primitiveFormat(targetElement, option));
                result.setChildren(Collections.emptyList());

                compareResultList.add(result);
            }













        }


        return Collections.emptyList();
    }


    private List<CompareResult> compareArray(Object origin, Object target, CompareOption option) {

        Class<?> beanClass = BeanInfoTool.getBeanClass(origin, target);
        if (origin == null) {
            origin = new Object[0];
        }
        if (target == null) {
            target = new Object[0];
        }
        List<Object> originList = new ArrayList<>(Arrays.asList((Object[]) origin));
        List<Object> targetList = new ArrayList<>(Arrays.asList((Object[]) target));
        return compareBaseCollection(originList, targetList, option);
    }


    private List<CompareResult> compareMap(Object origin, Object target, CompareOption option) {


        return Collections.emptyList();
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
            if (!CompareType.NONE.equals(result.getCompareType())) {
                return CompareType.CHANGE;
            }
        }

        return CompareType.NONE;
    }

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

        CompareType compareType = originValue.equals(targetValue) ? CompareType.NONE : CompareType.CHANGE;

        if (beanClass == null || !Comparable.class.isAssignableFrom(beanClass)) {
            // if not comparable, use equals
            return compareType;
        }
        try {
            @SuppressWarnings("unchecked")
            int compareResult = ((Comparable<Object>) originValue).compareTo(targetValue);
            if (compareResult != 0) {
                return CompareType.CHANGE;
            }
            return compareType;
        } catch (ClassCastException | NullPointerException e) {
            log.error("CompareError:{}", e.getMessage());
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
