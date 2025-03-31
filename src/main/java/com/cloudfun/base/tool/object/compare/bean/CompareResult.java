package com.cloudfun.base.tool.object.compare.bean;

import com.cloudfun.base.tool.object.compare.anno.Id;
import com.cloudfun.base.tool.object.compare.anno.Name;
import com.cloudfun.base.tool.object.compare.contants.CompareType;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/28 16:31
 */
public class CompareResult {


    /**
     * field
     * map key or bean field name or {@link Id} mark field
     */
    private String field;

    /**
     * fieldName
     * <p>
     * {@link Name} mark field or map key or bean field name
     */
    private String fieldName;

    /**
     * change type
     */
    private CompareType compareType;


    /**
     * origin value
     * <p>
     * all type convert to  string
     */
    private String oldValue;

    /**
     * after change value
     * <p>
     * all type convert to string
     */
    private String newValue;


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

    public CompareType getCompareType() {
        return compareType;
    }

    public void setCompareType(CompareType compareType) {
        this.compareType = compareType;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
}
