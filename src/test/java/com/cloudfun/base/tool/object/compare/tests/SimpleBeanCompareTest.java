package com.cloudfun.base.tool.object.compare.tests;

import com.cloudfun.base.tool.object.compare.bean.CompareResult;
import com.cloudfun.base.tool.object.compare.bean.SimpleBean;
import com.cloudfun.base.tool.object.compare.bean.SimpleBean1;
import com.cloudfun.base.tool.object.compare.bean.SimpleBeanCollection;
import com.cloudfun.base.tool.object.compare.bean.SimpleBeanWithAnno;
import com.cloudfun.base.tool.object.compare.bean.SimpleBeanWithAnno1;
import com.cloudfun.base.tool.object.compare.contants.CompareType;
import com.cloudfun.base.tool.object.compare.exception.CompareException;
import com.cloudfun.base.tool.object.compare.option.CompareOption;
import com.cloudfun.base.tool.object.compare.tool.BeanCompare;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/5/13 10:22
 */

public class SimpleBeanCompareTest {

    Logger logger = LoggerFactory.getLogger(SimpleBeanCompareTest.class);

    @Test
    @DisplayName("testNull")
    public void testNull() {
        BeanCompare compare = new BeanCompare();
        List<CompareResult> compareResult = compare.compare(null, null, null);
        Assertions.assertEquals(0, compareResult.size());
    }

    @Test
    @DisplayName("testDifBean")
    public void testDifBean() {
        Assertions.assertThrowsExactly(CompareException.class, () -> {
            BeanCompare compare = new BeanCompare();
            List<CompareResult> compareResult = compare.compare(new SimpleBean(), new SimpleBean1(), null);

        });
    }

    @Test
    @DisplayName("tesSimpleCompare")
    public void tesSimpleCompare() {
        SimpleBean originBean = SimpleBean.createOriginBean();
        SimpleBean targetBean = SimpleBean.createTargetBean();


        CompareOption option = CompareOption.DEFAULT;
        option.setOnlyNameAnnotation(false);

        BeanCompare compare = new BeanCompare();
        List<CompareResult> compareResult = compare.compare(originBean, targetBean, option);
        Assertions.assertEquals(50, compareResult.size());
        for (int i = 0; i < 50; i++) {
            if (Integer.valueOf(42).equals(i) || Integer.valueOf(45).equals(i)) {
                Assertions.assertEquals(CompareType.NONE, compareResult.get(i).getCompareType());
            } else {
                // System.out.println(compareResult.get(i).getFieldName());
                Assertions.assertEquals(CompareType.CHANGE, compareResult.get(i).getCompareType());
            }
        }

        // String print = CompareResultPrint.print(compareResult, option);

        // logger.info(print);

    }

    @Test
    @DisplayName("tesSimpleCompareNull")
    public void tesSimpleCompareNull() {
        SimpleBean originBean = SimpleBean.createOriginBean();
        SimpleBean targetBean = null;


        CompareOption option = CompareOption.DEFAULT;
        option.setOnlyNameAnnotation(false);

        BeanCompare compare = new BeanCompare();
        List<CompareResult> compareResult = compare.compare(originBean, targetBean, option);
        Assertions.assertEquals(50, compareResult.size());
        for (int i = 0; i < 50; i++) {
            if (Integer.valueOf(42).equals(i)) {
                Assertions.assertEquals(CompareType.NONE, compareResult.get(i).getCompareType());
            } else {
                // System.out.println(compareResult.get(i).getFieldName());
                Assertions.assertEquals(CompareType.DELETE, compareResult.get(i).getCompareType());
            }
        }

        // String print = CompareResultPrint.print(compareResult, option);

        // logger.info(print);

    }




    @Test
    @DisplayName("tesSimpleCompareWithOutNameAnno")
    public void tesSimpleCompareWithOutNameAnno() {
        SimpleBean1 originBean = SimpleBean1.createOriginBean();
        SimpleBean1 targetBean = SimpleBean1.createTargetBean();


        CompareOption option = new CompareOption();
        option.setOnlyNameAnnotation(true);

        BeanCompare compare = new BeanCompare();
        List<CompareResult> compareResult = compare.compare(originBean, targetBean, option);
        Assertions.assertEquals(0, compareResult.size());

    }

    @Test
    @DisplayName("tesSimpleCompareWithNameAnno")
    public void tesSimpleCompareWithNameAnno() {
        SimpleBeanWithAnno originBean = SimpleBeanWithAnno.createOriginBean();
        SimpleBeanWithAnno targetBean = SimpleBeanWithAnno.createTargetBean();


        CompareOption option = new CompareOption();

        BeanCompare compare = new BeanCompare();
        List<CompareResult> compareResult = compare.compare(originBean, targetBean, option);
        Assertions.assertEquals(50, compareResult.size());

        for (int i = 0; i < 50; i++) {
            if (Integer.valueOf(42).equals(i) || Integer.valueOf(45).equals(i)) {
                Assertions.assertEquals(CompareType.NONE, compareResult.get(i).getCompareType());
            } else {
                // System.out.println(compareResult.get(i).getFieldName());
                Assertions.assertEquals(CompareType.CHANGE, compareResult.get(i).getCompareType());
            }


            Assertions.assertEquals(("name"+compareResult.get(i).getField()).toUpperCase(),
                    compareResult.get(i).getFieldName().toUpperCase());

        }
    }

    @Test
    @DisplayName("tesSimpleCompareWithIgnoreAnno")
    public void tesSimpleCompareWithIgnoreAnno() {
        SimpleBeanWithAnno1 originBean = SimpleBeanWithAnno1.createOriginBean();
        SimpleBeanWithAnno1 targetBean = SimpleBeanWithAnno1.createTargetBean();


        CompareOption option = new CompareOption();

        BeanCompare compare = new BeanCompare();
        List<CompareResult> compareResult = compare.compare(originBean, targetBean, option);
        Assertions.assertEquals(49, compareResult.size());

    }

    @Test
    @DisplayName("tesSimpleCompareIgnoreSort")
    public void tesSimpleCompareIgnoreSort() {
        SimpleBeanCollection originBean = SimpleBeanCollection.createOriginBean();
        SimpleBeanCollection targetBean = SimpleBeanCollection.createTargetBean();


        CompareOption option = new CompareOption();

        BeanCompare compare = new BeanCompare();
        List<CompareResult> compareResult = compare.compare(originBean, targetBean, option);

        Assertions.assertEquals(CompareType.NONE, compareResult.get(0).getCompareType());

        Assertions.assertEquals(4, compareResult.get(0).getChildren().size());


    }



}
