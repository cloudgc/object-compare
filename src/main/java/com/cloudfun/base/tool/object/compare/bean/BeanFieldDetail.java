package com.cloudfun.base.tool.object.compare.bean;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 15:38
 */
public class BeanFieldDetail {
    public BeanFieldDetail() {
    }

    public BeanFieldDetail(String field, String fieldName, Field fieldProperty, PropertyDescriptor propertyDescriptor) {
        this.field = field;
        this.fieldName = fieldName;
        this.fieldProperty = fieldProperty;
        this.propertyDescriptor = propertyDescriptor;
    }

    private String field;

    private String fieldName;

    private Field fieldProperty;

    private PropertyDescriptor propertyDescriptor;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Field getFieldProperty() {
        return fieldProperty;
    }

    public void setFieldProperty(Field fieldProperty) {
        this.fieldProperty = fieldProperty;
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }

    public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
    }
}

