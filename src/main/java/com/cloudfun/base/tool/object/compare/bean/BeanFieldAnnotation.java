package com.cloudfun.base.tool.object.compare.bean;

import java.util.List;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 11:41
 */
public class BeanFieldAnnotation {


    private BeanFieldDetail idField;


    private List<BeanFieldDetail> nameFieldList;

    public BeanFieldDetail getIdField() {
        return idField;
    }

    public void setIdField(BeanFieldDetail idField) {
        this.idField = idField;
    }

    public List<BeanFieldDetail> getNameFieldList() {
        return nameFieldList;
    }

    public void setNameFieldList(List<BeanFieldDetail> nameFieldList) {
        this.nameFieldList = nameFieldList;
    }

    public BeanFieldAnnotation(BeanFieldDetail idField, List<BeanFieldDetail> nameFieldList) {
        this.idField = idField;
        this.nameFieldList = nameFieldList;
    }
}
