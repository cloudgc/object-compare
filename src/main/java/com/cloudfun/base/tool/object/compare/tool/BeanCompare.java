package com.cloudfun.base.tool.object.compare.tool;

import com.cloudfun.base.tool.object.compare.bean.BeanFieldAnnotation;
import com.cloudfun.base.tool.object.compare.bean.BeanFieldDetail;
import com.cloudfun.base.tool.object.compare.bean.CompareResult;
import com.cloudfun.base.tool.object.compare.contants.CompareType;
import com.cloudfun.base.tool.object.compare.exception.CompareException;
import com.cloudfun.base.tool.object.compare.option.CompareOption;
import com.cloudfun.base.tool.object.compare.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
        if (BeanInfoTool.isNotSameClass(origin, target)) {
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
                    compareChildBean = compareCollection(originValue, targetValue, option, beanFieldDetail);
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
     * support map list set  array
     *
     * @param origin          origin collection
     * @param target          target collection
     * @param option          compare option
     * @param beanFieldDetail collection field detail
     * @return compare result list
     */

    public List<CompareResult> compareCollection(Object origin, Object target, CompareOption option,
                                                 BeanFieldDetail beanFieldDetail) {
        if (origin == null && target == null) {
            return Collections.emptyList();
        }

        if (BeanInfoTool.isNotSameClass(origin, target)) {
            throw new CompareException("origin and target must be same type");
        }

        Class<?> beanClass = BeanInfoTool.getBeanClass(origin, target);
        if (!BeanInfoTool.isCollectionType(beanClass)) {
            return Collections.emptyList();
        }

        if (beanClass.isArray()) {
            return compareArray(origin, target, option, beanFieldDetail);
        }

        if (Map.class.isAssignableFrom(beanClass)) {
            return compareMap(origin, target, option);
        }


        return compareBaseCollection((Collection<?>) origin, (Collection<?>) target, option, beanFieldDetail);
    }


    private List<CompareResult> compareBaseCollection(Collection<?> origin, Collection<?> target,
                                                      CompareOption option, BeanFieldDetail beanFieldDetail) {

        Class<?> beanClass = BeanInfoTool.getCollectionRawClass(origin, target);
        if (Object.class.equals(beanClass)) {
            return Collections.emptyList();
        }

        // for iterator
        if (origin == null) {
            origin = Collections.emptyList();
        }
        if (target == null) {
            target = Collections.emptyList();
        }

        // collection compare by value set
        if (BeanInfoTool.getIgnoreArraySort(beanFieldDetail)) {
            return compareBaseCollectionIgnoreSort(origin, target, option, beanFieldDetail, BeanInfoTool.isPrimitive(beanClass));
        }

        boolean hasIdField = BeanInfoTool.hasIdField(origin, target, option);
        if (hasIdField) {
            return compareCollectionWithId(origin, target, beanFieldDetail, option, BeanInfoTool.isPrimitive(beanClass));
        }

        // compare collection by sort
        return compareBaseCollectionBySort(origin, target, option, beanFieldDetail, BeanInfoTool.isPrimitive(beanClass));
    }

    private List<CompareResult> compareBaseCollectionIgnoreSort(Collection<?> origin, Collection<?> target, CompareOption option, BeanFieldDetail beanFieldDetail, boolean primitive) {
        int maxSize = Math.max(origin.size(), target.size());
        Iterator<?> originIterator = origin.iterator();
        Iterator<?> targetIterator = target.iterator();

        Map<Object, Deque<Object>> originIndexMap = new LinkedHashMap<>();
        Map<Object, Deque<Object>> targetIndexMap = new LinkedHashMap<>();

        Set<Object> allIndexKey = new HashSet<>();


        for (int i = 0; i < maxSize; i++) {
            Object originElement = originIterator.hasNext() ? originIterator.next() : null;

            if (originElement != null) {


                Optional<Map.Entry<Object, Deque<Object>>> originEntry = originIndexMap.entrySet().stream().filter(key -> ObjectUtil.isSame(key.getKey(),
                        originElement)).findFirst();
                if (originEntry.isEmpty()) {
                    Deque<Object> originDeque = new ArrayDeque<>();
                    originDeque.push(originElement);
                    originIndexMap.put(originElement, originDeque);
                } else {
                    originEntry.get().getValue().push(originElement);
                }


                boolean originNotMatch = allIndexKey.stream().noneMatch(key -> ObjectUtil.isSame(key, originElement));
                if (originNotMatch) {
                    allIndexKey.add(originElement);
                }
            }

            Object targetElement = targetIterator.hasNext() ? targetIterator.next() : null;

            if (targetElement != null) {

                Optional<Map.Entry<Object, Deque<Object>>> targetEntry = targetIndexMap.entrySet().stream().filter(key -> ObjectUtil.isSame(key.getKey(),
                        originElement)).findFirst();
                if (targetEntry.isEmpty()) {
                    Deque<Object> targetDeque = new ArrayDeque<>();
                    targetDeque.push(originElement);
                    targetIndexMap.put(originElement, targetDeque);
                } else {
                    targetEntry.get().getValue().push(originElement);
                }


                boolean targetNotMatch = allIndexKey.stream().noneMatch(key -> ObjectUtil.isSame(key, targetElement));
                if (targetNotMatch) {
                    allIndexKey.add(targetElement);
                }
            }

        }

        int fieldIndex = 0;
        List<CompareResult> compareResultList = new ArrayList<>();
        for (Object element : allIndexKey) {

            Optional<Map.Entry<Object, Deque<Object>>> originEntry = originIndexMap.entrySet().stream()
                    .filter(key -> ObjectUtil.isSame(key.getKey(), element)).findFirst();


            Optional<Map.Entry<Object, Deque<Object>>> targetEntry = targetIndexMap.entrySet().stream()
                    .filter(key -> ObjectUtil.isSame(key.getKey(), element)).findFirst();


            int originMax = 0;
            int targetMax = 0;
            if (originEntry.isPresent() &&  !originEntry.get().getValue().isEmpty()) {
                originMax = originEntry.get().getValue().size();
                // originElement = originEntry.get().getValue().pop();
            }

            if (targetEntry.isPresent() &&  !targetEntry.get().getValue().isEmpty()) {
                // targetElement = targetEntry.get().getValue().pop();
                targetMax = targetEntry.get().getValue().size();
            }

            int valueMax = Math.max(originMax, targetMax);
            for (int i = 0; i < valueMax; i++) {
            Object originElement = null;

            Object targetElement = null;

                if (originEntry.isPresent() &&  !originEntry.get().getValue().isEmpty()) {
                    originElement = originEntry.get().getValue().pop();
                }

                if (targetEntry.isPresent() &&  !targetEntry.get().getValue().isEmpty()) {
                    targetElement = targetEntry.get().getValue().pop();
                }

                //   primitive type collection
                if (primitive) {
                    CompareResult result = new CompareResult();
                    result.setField(Integer.toString(fieldIndex));
                    result.setFieldName(Integer.toString(fieldIndex));
                    result.setCompareType(getCompareValueType(originElement, targetElement));
                    result.setOriginValue(BeanValueFormat.primitiveFormat(originElement, beanFieldDetail, option));
                    result.setTargetValue(BeanValueFormat.primitiveFormat(targetElement, beanFieldDetail, option));
                    result.setChildren(Collections.emptyList());
                    compareResultList.add(result);
                } else {
                    // ref bean type collection
                    CompareResult result = new CompareResult();
                    result.setField(Integer.toString(fieldIndex));
                    result.setFieldName(result.getField());

                    List<CompareResult> compareChildren = this.compare(originElement, targetElement, option);
                    result.setChildren(compareChildren);

                    CompareType compareType = this.getCompareTypeByResultList(compareChildren, originElement, targetElement);
                    result.setCompareType(compareType);
                    compareResultList.add(result);
                }
                fieldIndex++;
            }

        }
        return compareResultList;
    }

    private List<CompareResult> compareBaseCollectionBySort(Collection<?> origin, Collection<?> target, CompareOption option, BeanFieldDetail beanFieldDetail, boolean primitive) {
        int maxSize = Math.max(origin.size(), target.size());
        Iterator<?> originIterator = origin.iterator();
        Iterator<?> targetIterator = target.iterator();
        List<CompareResult> compareResultList = new ArrayList<>();
        for (int i = 0; i < maxSize; i++) {
            Object originElement = originIterator.hasNext() ? originIterator.next() : null;
            Object targetElement = targetIterator.hasNext() ? targetIterator.next() : null;

            //   primitive type collection
            if (primitive) {
                CompareResult result = new CompareResult();
                result.setField(Integer.toString(i));
                result.setFieldName(result.getField());
                result.setCompareType(getCompareValueType(originElement, targetElement));
                result.setOriginValue(BeanValueFormat.primitiveFormat(originElement, beanFieldDetail, option));
                result.setTargetValue(BeanValueFormat.primitiveFormat(targetElement, beanFieldDetail, option));
                result.setChildren(Collections.emptyList());
                compareResultList.add(result);
            } else {
                // ref bean type collection
                CompareResult result = new CompareResult();
                result.setField(Integer.toString(i));
                result.setFieldName(result.getField());

                List<CompareResult> compareChildren = this.compare(originElement, targetElement, option);
                result.setChildren(compareChildren);

                CompareType compareType = this.getCompareTypeByResultList(compareChildren, originElement, targetElement);
                result.setCompareType(compareType);
                compareResultList.add(result);
            }

        }
        return compareResultList;
    }


    private List<CompareResult> compareCollectionWithId(Collection<?> origin, Collection<?> target, BeanFieldDetail beanFieldDetail, CompareOption option, boolean primitive) {
        int maxSize = Math.max(origin.size(), target.size());
        Iterator<?> originIterator = origin.iterator();
        Iterator<?> targetIterator = target.iterator();

        Map<Object, Object> orginMap = new HashMap<>(maxSize);
        Map<Object, Object> targetMap = new HashMap<>(maxSize);

        Set<Object> allIdIndex = new LinkedHashSet<>();

        for (int i = 0; i < maxSize; i++) {

            Object originElement = originIterator.hasNext() ? originIterator.next() : null;
            Object targetElement = targetIterator.hasNext() ? targetIterator.next() : null;

            BeanFieldAnnotation field = BeanInfoTool.getFieldList(originElement, targetElement, option);

            if (field.getIdField() == null) {
                continue;
            }

            BeanFieldDetail beanIdField = field.getIdField();
            Method readMethod = beanIdField.getPropertyDescriptor().getReadMethod();
            Object originIdValue = getFieldValue(readMethod, originElement);
            Object targetIdValue = getFieldValue(readMethod, targetElement);

            if (originIdValue != null) {
                allIdIndex.add(originIdValue);
                orginMap.put(originIdValue, originElement);
            }

            if (targetIdValue != null) {
                allIdIndex.add(targetIdValue);
                targetMap.put(targetIdValue, targetElement);
            }
        }

        List<CompareResult> compareResultList = new ArrayList<>();

        int fieldIndex = 0;

        for (Object idIndex : allIdIndex) {
            Object originElement = orginMap.get(idIndex);
            Object targetElement = targetMap.get(idIndex);


            //   primitive type collection
            if (primitive) {
                CompareResult result = new CompareResult();
                result.setField(Integer.toString(fieldIndex));
                result.setFieldName(Integer.toString(fieldIndex));
                result.setCompareType(getCompareValueType(originElement, targetElement));
                result.setOriginValue(BeanValueFormat.primitiveFormat(originElement, beanFieldDetail, option));
                result.setTargetValue(BeanValueFormat.primitiveFormat(targetElement, beanFieldDetail, option));
                result.setChildren(Collections.emptyList());
                compareResultList.add(result);
            } else {
                // ref bean type collection
                CompareResult result = new CompareResult();
                result.setField(Integer.toString(fieldIndex));
                result.setFieldName(result.getField());

                List<CompareResult> compareChildren = this.compare(originElement, targetElement, option);
                result.setChildren(compareChildren);

                CompareType compareType = this.getCompareTypeByResultList(compareChildren, originElement, targetElement);
                result.setCompareType(compareType);
                compareResultList.add(result);
            }

            fieldIndex++;
        }
        return compareResultList;
    }


    private List<CompareResult> compareArray(Object origin, Object target, CompareOption option, BeanFieldDetail beanFieldDetail) {

        if (origin == null) {
            origin = new Object[0];
        }
        if (target == null) {
            target = new Object[0];
        }

        Class<?> beanClass = origin.getClass().getComponentType();

        if (beanClass != null && beanClass.isPrimitive()) {
            List<Object> originList = BeanInfoTool.convertPrimitiveArray(origin);
            List<Object> targetList = BeanInfoTool.convertPrimitiveArray(target);
            return compareBaseCollection(originList, targetList, option, beanFieldDetail);

        } else {
            Object[] originArray = (Object[]) origin;
            Object[] targetArray = (Object[]) target;
            List<Object> originList = new ArrayList<>(Arrays.asList(originArray));
            List<Object> targetList = new ArrayList<>(Arrays.asList(targetArray));
            return compareBaseCollection(originList, targetList, option, beanFieldDetail);
        }
    }


    private List<CompareResult> compareMap(Object origin, Object target, CompareOption option) {

        if (origin == null) {
            origin = Collections.emptyMap();
        }
        if (target == null) {
            target = Collections.emptyMap();
        }

        LinkedHashSet<Object> allCompareKey = new LinkedHashSet<>();
        Map<?, ?> originMap = (Map<?, ?>) origin;
        Map<?, ?> targetMap = (Map<?, ?>) target;

        allCompareKey.addAll(originMap.keySet());
        allCompareKey.addAll(targetMap.keySet());

        Class<?> beanClass = BeanInfoTool.getMapValueClass(origin, target);

        List<CompareResult> compareResultList = new ArrayList<>();
        for (Object key : allCompareKey) {
            if (key == null) {
                continue;
            }

            Object originValue = ((Map<?, ?>) origin).get(key);
            Object targetValue = ((Map<?, ?>) target).get(key);

            if (BeanInfoTool.isPrimitive(beanClass)) {
                CompareResult result = new CompareResult();
                result.setField(key.toString());
                result.setFieldName(key.toString());
                result.setCompareType(getCompareValueType(originValue, targetValue));
                result.setOriginValue(BeanValueFormat.primitiveFormat(originValue, option));
                result.setTargetValue(BeanValueFormat.primitiveFormat(targetValue, option));
                result.setChildren(Collections.emptyList());
                compareResultList.add(result);
            } else {
                // ref bean type collection
                CompareResult result = new CompareResult();
                result.setField(key.toString());
                result.setFieldName(key.toString());

                List<CompareResult> compareChildren = this.compare(originValue, targetValue, option);
                result.setChildren(compareChildren);

                CompareType compareType = this.getCompareTypeByResultList(compareChildren, originValue, targetValue);
                result.setCompareType(compareType);
                compareResultList.add(result);
            }


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

        if (!Comparable.class.isAssignableFrom(beanClass)) {
            return originValue.equals(targetValue) ? CompareType.NONE : CompareType.CHANGE;
        }

        try {
            if (Comparable.class.isAssignableFrom(beanClass)) {
                @SuppressWarnings("unchecked") int compareResult = ((Comparable<Object>) originValue).compareTo(targetValue);
                if (compareResult != 0) {
                    return CompareType.CHANGE;
                }
                return CompareType.NONE;
            }
        } catch (ClassCastException | NullPointerException e) {
            log.error("CompareError:{}", e.getMessage());
            return CompareType.CHANGE;
        }
        return CompareType.NONE;
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
