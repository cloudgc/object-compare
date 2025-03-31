package com.cloudfun.base.tool.object.compare;

import com.cloudfun.base.tool.object.compare.bean.BeanFieldAnnotation;
import com.cloudfun.base.tool.object.compare.option.CompareOption;
import com.cloudfun.base.tool.object.compare.tool.BeanInfoTool;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/28 16:48
 */
class ObjectCompareTest {


    @Test
    public void test1() {
        //
        // BeanFieldAnnotation fieldList = BeanInfoTool.getFieldList(new User(), null, CompareOption.DEFAULT);
        // Assertions.assertAll("getField", () -> {
        //     Assertions.assertNotNull(fieldList.getIdField());
        // });

        CompareOption compareOption = new CompareOption();
        compareOption.setOnlyNameAnnotation(false);

        BeanFieldAnnotation fieldListA = BeanInfoTool.getFieldList(new User(), null, compareOption);
        Assertions.assertAll("getField", () -> {
            Assertions.assertNotNull(fieldListA.getIdField());
        });

    }
}