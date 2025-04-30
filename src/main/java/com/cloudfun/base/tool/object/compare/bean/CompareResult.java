package com.cloudfun.base.tool.object.compare.bean;

import com.cloudfun.base.tool.object.compare.anno.Id;
import com.cloudfun.base.tool.object.compare.anno.Name;
import com.cloudfun.base.tool.object.compare.contants.CompareType;

import java.util.List;

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
    private String originValue;

    /**
     * after change value
     * <p>
     * all type convert to string
     */
    private String targetValue;

    /**
     * inner bean the ref field
     */
    private List<CompareResult> children;

    private boolean isCollection;

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

    public String getOriginValue() {
        return originValue;
    }

    public void setOriginValue(String originValue) {
        this.originValue = originValue;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public List<CompareResult> getChildren() {
        return children;
    }

    public void setChildren(List<CompareResult> children) {
        this.children = children;
    }

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
    }
}
