package com.cloudfun.base.tool.object.compare.tool;

import com.cloudfun.base.tool.object.compare.anno.Id;
import com.cloudfun.base.tool.object.compare.anno.Ignore;
import com.cloudfun.base.tool.object.compare.anno.Name;
import com.cloudfun.base.tool.object.compare.bean.BeanFieldAnnotation;
import com.cloudfun.base.tool.object.compare.bean.BeanFieldDetail;
import com.cloudfun.base.tool.object.compare.exception.CompareException;
import com.cloudfun.base.tool.object.compare.option.CompareOption;
import com.cloudfun.base.tool.object.compare.util.ObjectUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 9:45
 */
public class BeanInfoTool {


    private static final Map<Class<?>, BeanInfo> BEAN_INFO_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, BeanFieldAnnotation> BEAN_FIELD_CACHE = new ConcurrentHashMap<>();

    public static Class<?> getBeanClass(Object origin, Object target) {
        if (origin == null && target == null) {
            return Object.class;
        }
        return origin == null ? target.getClass() : origin.getClass();
    }


    public static Class<?> getCollectionRawClass(Object origin, Object target) {

        if (ObjectUtil.isEmpty(origin) && ObjectUtil.isEmpty(target)) {
            return Object.class;
        }

        if (origin != null && !isCollectionType(origin.getClass())) {
            return Object.class;
        }

        if (target != null && !isCollectionType(target.getClass())) {
            return Object.class;
        }


        Collection<?> collection = ObjectUtil.isEmpty(origin) ? (Collection<?>) target : (Collection<?>) origin;

        if (collection.isEmpty()) {
            return Object.class;
        }
        Object next = collection.iterator().next();
        return next.getClass();
    }

    public static Class<?> getMapValueClass(Object origin, Object target) {
        if (ObjectUtil.isEmpty(origin) && ObjectUtil.isEmpty(target)) {
            return Object.class;
        }

        if (origin != null && !isCollectionType(origin.getClass())) {
            return Object.class;
        }

        if (target != null && !isCollectionType(target.getClass())) {
            return Object.class;
        }


        Map<?, ?> collection = ObjectUtil.isEmpty(origin) ? (Map<?, ?>) origin : (Map<?, ?>) target;
        if (collection.isEmpty()) {
            return Object.class;
        }
        Object next = collection.values().iterator().next();

        return next.getClass();

    }


    private static BeanInfo getBeanInfo(Object origin, Object target) {

        Class<?> compareClass = getBeanClass(origin, target);

        BeanInfo beanInfo = BEAN_INFO_CACHE.get(compareClass);

        if (beanInfo == null) {
            try {
                beanInfo = Introspector.getBeanInfo(compareClass);
                BEAN_INFO_CACHE.put(compareClass, beanInfo);
            } catch (IntrospectionException e) {
                throw new CompareException(e.getMessage() + ":" + compareClass + ": get bean info error");
            }
        }
        return beanInfo;
    }

    private static BeanInfo getBeanInfoInSelf(Object origin, Object target) {

        Class<?> compareClass = getBeanClass(origin, target);

        try {
            return Introspector.getBeanInfo(compareClass, compareClass.getSuperclass());
        } catch (IntrospectionException e) {
            throw new CompareException(e.getMessage() + ":" + compareClass + ": get bean info error");
        }
    }


    public static BeanFieldAnnotation getFieldList(Object origin, Object target, CompareOption option) {

        Class<?> beanClass = getBeanClass(origin, target);

        BeanFieldAnnotation beanFieldCache = BEAN_FIELD_CACHE.get(beanClass);
        if (beanFieldCache != null) {
            return beanFieldCache;
        }

        // get bean info
        BeanInfo beanInfo = getBeanInfo(origin, target);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        if (propertyDescriptors == null) {
            return new BeanFieldAnnotation(null, Collections.emptyList());
        }

        // get base bean info without parent class
        BeanInfo beanInfoInSelf = getBeanInfoInSelf(origin, target);
        PropertyDescriptor[] propertyDescriptorsInSelf = beanInfoInSelf.getPropertyDescriptors();

        List<PropertyDescriptor> propertyDescriptorsInSelfList = Arrays.asList(propertyDescriptorsInSelf);

        List<PropertyDescriptor> propertyDescriptorsList = Arrays.asList(propertyDescriptors);


        BeanFieldDetail idMethod = null;

        List<BeanFieldDetail> nameMethodList = new ArrayList<>();

        for (Field declaredField : beanClass.getDeclaredFields()) {

            // must define as property (with read and write method)
            Optional<PropertyDescriptor> beanProperDesc = propertyDescriptorsList.stream()
                    .filter(p -> p.getDisplayName().equals(declaredField.getName())).findFirst();
            if (beanProperDesc.isEmpty()) {
                continue;
            }

            if (declaredField.isAnnotationPresent(Ignore.class)) {
                continue;
            }


            if (declaredField.isAnnotationPresent(Id.class)) {
                Id annotation = declaredField.getAnnotation(Id.class);
                // must self level bean has id anno, so use or ignore
                if (propertyDescriptorsInSelfList.stream().anyMatch(p -> p.getDisplayName().equals(declaredField.getName()))) {

                    if (idMethod != null && idMethod.getFieldName().equals(getFieldName(annotation.value(), declaredField.getName()))) {
                        throw new CompareException("@Id annotation must be unique");
                    }

                    idMethod = new BeanFieldDetail(declaredField.getName(),
                            getFieldName(annotation.value(), declaredField.getName()),
                            declaredField,
                            beanProperDesc.get());
                } else {
                    nameMethodList.add(new BeanFieldDetail(declaredField.getName(),
                            getFieldName(annotation.value(), declaredField.getName()),
                            declaredField,
                            beanProperDesc.get()));
                }
                continue;
            }

            // use name anno or not
            if (option.getOnlyNameAnnotation()) {
                if (declaredField.isAnnotationPresent(Name.class)) {
                    Name annotation = declaredField.getAnnotation(Name.class);
                    nameMethodList.add(new BeanFieldDetail(declaredField.getName(),
                            getFieldName(annotation.value(), declaredField.getName()),
                            declaredField,
                            beanProperDesc.get()));
                }
            } else {
                nameMethodList.add(new BeanFieldDetail(declaredField.getName(), declaredField.getName(), declaredField, beanProperDesc.get()));
            }
        }
        BeanFieldAnnotation beanFieldAnnotation = new BeanFieldAnnotation(idMethod, nameMethodList);
        // find in parent
        findParentBeanField(beanClass.getSuperclass(), beanFieldAnnotation, option, propertyDescriptorsList);

        BEAN_FIELD_CACHE.put(beanClass, beanFieldAnnotation);
        beanFieldCache = beanFieldAnnotation;
        return beanFieldCache;

    }

    private static String getFieldName(String annotationValue, String fieldName) {
        if (annotationValue == null || annotationValue.isEmpty()) {
            return fieldName;
        }
        return annotationValue;
    }


    private static void findParentBeanField(Class<?> beanClass, BeanFieldAnnotation beanFieldAnnotation,
                                            CompareOption option, List<PropertyDescriptor> propertyDescriptorsList) {
        if (beanClass.getSuperclass() == null || Object.class.equals(beanClass)) {
            return;
        }
        for (Field declaredField : beanClass.getDeclaredFields()) {
            Optional<PropertyDescriptor> beanProperDesc = propertyDescriptorsList.stream().filter(p -> p.getDisplayName().equals(declaredField.getName()))
                    .findFirst();
            if (beanProperDesc.isEmpty()) {
                continue;
            }

            if (declaredField.isAnnotationPresent(Ignore.class)) {
                continue;
            }
            if (declaredField.isAnnotationPresent(Id.class)) {
                Id annotation = declaredField.getAnnotation(Id.class);
                if (beanFieldAnnotation.getIdField() == null) {
                    beanFieldAnnotation.setIdField(new BeanFieldDetail(declaredField.getName(), annotation.value(), declaredField, beanProperDesc.get()));
                } else {
                    beanFieldAnnotation.getNameFieldList().add(new BeanFieldDetail(declaredField.getName(), annotation.value(), declaredField, beanProperDesc.get()));
                }

                continue;
            }


            if (option.getOnlyNameAnnotation()) {
                if (declaredField.isAnnotationPresent(Name.class)) {
                    Name annotation = declaredField.getAnnotation(Name.class);
                    beanFieldAnnotation.getNameFieldList().add(new BeanFieldDetail(declaredField.getName(), annotation.value(), declaredField, beanProperDesc.get()));
                }
            } else {
                beanFieldAnnotation.getNameFieldList().add(new BeanFieldDetail(declaredField.getName(), declaredField.getName(), declaredField, beanProperDesc.get()));

            }
        }

        findParentBeanField(beanClass.getSuperclass(), beanFieldAnnotation, option, propertyDescriptorsList);
    }


    protected static boolean isPrimitive(Class<?> type) {
        if (type == null) {
            return true;
        }
        if (type.isPrimitive()) {
            return true;
        }
        if (String.class.isAssignableFrom(type)) {
            return true;
        }
        if (Boolean.class.isAssignableFrom(type)) {
            return true;
        }

        if (Short.class.isAssignableFrom(type)) {
            return true;
        }

        if (Integer.class.isAssignableFrom(type)) {
            return true;
        }

        if (Long.class.isAssignableFrom(type)) {
            return true;
        }
        if (Float.class.isAssignableFrom(type)) {
            return true;
        }
        if (Double.class.isAssignableFrom(type)) {
            return true;
        }

        if (Character.class.isAssignableFrom(type)) {
            return true;
        }

        if (BigDecimal.class.isAssignableFrom(type)) {
            return true;
        }

        if (TemporalAccessor.class.isAssignableFrom(type)) {
            return true;
        }
        if (Date.class.isAssignableFrom(type)) {
            return true;
        }
        if (java.sql.Date.class.isAssignableFrom(type)) {
            return true;
        }
        return Number.class.isAssignableFrom(type);
    }

    protected static boolean isCollectionType(Class<?> fieldType) {
        if (fieldType == null) {
            return false;
        }
        if (fieldType.isArray()) {
            return true;
        }

        if (Collection.class.isAssignableFrom(fieldType)) {
            return true;
        }

        return Map.class.isAssignableFrom(fieldType);

    }

    /**
     * check java bean mark {@link Id} field
     */
    public static boolean hasIdField(Object origin, Object target, CompareOption option) {

        if (origin == null || target == null) {
            return false;
        }

        Class<?> beanClass = BeanInfoTool.getBeanClass(origin, target);
        if (beanClass == null) {
            return false;
        }
        if (BeanInfoTool.isPrimitive(beanClass)) {
            return false;
        }


        if (BeanInfoTool.isCollectionType(beanClass)) {

            Class<?> collectionRawClass = BeanInfoTool.getCollectionRawClass(origin, target);
            if (BeanInfoTool.isPrimitive(collectionRawClass)) {
                return false;
            }
            if (BeanInfoTool.isCollectionType(collectionRawClass)) {
                return false;
            }
            Collection<?> originCollection = (Collection<?>) origin;
            Collection<?> targetCollection = (Collection<?>) target;
            if (ObjectUtil.isEmpty(originCollection) && ObjectUtil.isEmpty(targetCollection)) {
                return false;
            }
            Collection<?> collectionValue = ObjectUtil.isEmpty(originCollection) ? targetCollection : originCollection;
            Object next = collectionValue.stream().iterator().next();
            BeanFieldAnnotation fieldList = BeanInfoTool.getFieldList(next, null, option);
            return fieldList.getIdField() != null;
        }

        BeanFieldAnnotation fieldList = BeanInfoTool.getFieldList(origin, target, option);
        return fieldList.getIdField() != null;
    }

    public static List<Object> convertPrimitiveArray(Object object) {
        switch (object) {
            case null -> {
                return Collections.emptyList();
            }
            case byte[] bytes -> {
                List<Object> list = new ArrayList<>();
                for (byte b : bytes) {
                    list.add(b);
                }
                return list;
            }
            case short[] shorts -> {
                List<Object> list = new ArrayList<>();
                for (short s : shorts) {
                    list.add(s);
                }
                return list;
            }
            case int[] ints -> {
                List<Object> list = new ArrayList<>();
                for (int i : ints) {
                    list.add(i);
                }
                return list;
            }
            case long[] longs -> {
                List<Object> list = new ArrayList<>();
                for (long l : longs) {
                    list.add(l);
                }
                return list;
            }
            case float[] floats -> {
                List<Object> list = new ArrayList<>();
                for (float f : floats) {
                    list.add(f);
                }
                return list;

            }
            case double[] doubles -> {
                List<Object> list = new ArrayList<>();
                for (double d : doubles) {
                    list.add(d);
                }
                return list;
            }
            case char[] chars -> {
                List<Object> list = new ArrayList<>();
                for (char c : chars) {
                    list.add(c);
                }
                return list;
            }
            case boolean[] booleans -> {
                List<Object> list = new ArrayList<>();
                for (boolean b : booleans) {
                    list.add(b);
                }
                return list;
            }
            case Object[] objects -> {
                return new ArrayList<>(Arrays.asList(objects));
            }
            default -> {
            }
        }

        return Collections.emptyList();
    }

    public static boolean isNotSameClass(Object origin, Object target) {
        if (origin == null || target == null) {
            return false;
        }

        return !isSameClass(origin.getClass(), target.getClass());
    }

    private static boolean isSameClass(Class<?> origin, Class<?> target) {

        if (origin == null || target == null) {
            return false;
        }

        if (origin.equals(target)) {
            return true;
        }
        if (origin.getSuperclass() != null && !Object.class.equals(origin.getSuperclass())) {
            return isSameClass(origin.getSuperclass(), target.getSuperclass());
        }
        return false;
    }


    public static boolean getIgnoreArraySort(Field fieldProperty) {
        if (fieldProperty == null) {
            return false;
        }

        if (!fieldProperty.isAnnotationPresent(Name.class)) {
            return false;
        }
        Name annotation = fieldProperty.getAnnotation(Name.class);
        return annotation.ignoreArraySort();
    }
}
