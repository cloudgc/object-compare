package com.cloudfun.base.tool.object.compare.tool;

import com.cloudfun.base.tool.object.compare.anno.Id;
import com.cloudfun.base.tool.object.compare.anno.Ignore;
import com.cloudfun.base.tool.object.compare.anno.Name;
import com.cloudfun.base.tool.object.compare.bean.BeanFieldAnnotation;
import com.cloudfun.base.tool.object.compare.bean.BeanFieldDetail;
import com.cloudfun.base.tool.object.compare.exception.CompareException;
import com.cloudfun.base.tool.object.compare.option.CompareOption;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Arrays;
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
        return origin == null ? target.getClass() : origin.getClass();
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


        Class<?> beanClass = getBeanClass(origin, target);

        BeanFieldAnnotation beanFieldCache = BEAN_FIELD_CACHE.get(beanClass);

        if (beanFieldCache == null) {


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
        }
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


    public static boolean isPrimitive(Class<?> type) {
        if (type == null) {
            return true;
        }
        if (type.isPrimitive()) {
            return true;
        }
        if (String.class.isAssignableFrom(type)) {
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
        if (Number.class.isAssignableFrom(type)) {
            return true;
        }

        return false;
    }

}
