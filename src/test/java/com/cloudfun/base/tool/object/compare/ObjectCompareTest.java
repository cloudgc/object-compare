package com.cloudfun.base.tool.object.compare;

import com.cloudfun.base.tool.object.compare.bean.BeanFieldAnnotation;
import com.cloudfun.base.tool.object.compare.bean.CompareResult;
import com.cloudfun.base.tool.object.compare.contants.CompareType;
import com.cloudfun.base.tool.object.compare.option.CompareOption;
import com.cloudfun.base.tool.object.compare.tool.BeanCompare;
import com.cloudfun.base.tool.object.compare.tool.BeanInfoTool;
import com.cloudfun.base.tool.object.compare.tool.CompareResultPrint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/28 16:48
 */
class ObjectCompareTest {


    @Test
    public void test1() {
        CompareOption compareOption = new CompareOption();
        compareOption.setOnlyNameAnnotation(false);

        BeanFieldAnnotation fieldListA = BeanInfoTool.getFieldList(new User(), null, compareOption);
        Assertions.assertAll("getField", () -> {
            Assertions.assertNotNull(fieldListA.getIdField());
        });

    }

    @Test
    public void testPrint() {

        List<CompareResult> list = new ArrayList<>();
        CompareResult result = new CompareResult();
        result.setField("age");
        result.setFieldName("年龄");
        result.setCompareType(CompareType.CHANGE);
        result.setOriginValue("18");
        result.setTargetValue("20");
        result.setCollection(false);
        list.add(result);

        CompareResult result1 = new CompareResult();
        result1.setField("name");
        result1.setFieldName("名称");
        result1.setCompareType(CompareType.ADD);
        result1.setOriginValue(null);
        result1.setTargetValue("Tom");
        result1.setCollection(false);
        list.add(result1);

        CompareResult result2 = new CompareResult();
        result2.setField("address");
        result2.setFieldName("地址");
        result2.setCompareType(CompareType.ADD);
        result2.setOriginValue(null);
        result2.setTargetValue(null);
        result2.setCollection(true);

        List<CompareResult> list2 = new ArrayList<>();
        CompareResult result3 = new CompareResult();
        result3.setField("city");
        result3.setFieldName("城市");
        result3.setCompareType(CompareType.CHANGE);
        result3.setOriginValue("上海");
        result3.setTargetValue("北京");
        result3.setCollection(false);
        list2.add(result3);


        CompareResult result4 = new CompareResult();
        result4.setField("address");
        result4.setFieldName("地址");
        result4.setCompareType(CompareType.CHANGE);
        result4.setOriginValue("南京");
        result4.setTargetValue("杭州");
        result4.setCollection(false);
        list2.add(result4);

        result2.setChildren(list2);
        list.add(result2);

        CompareOption aDefault = CompareOption.DEFAULT;
        aDefault.setCompareTypeFormat(type -> switch (type) {
            case CompareType.ADD -> "增";
            case CompareType.CHANGE -> "改";
            case CompareType.DELETE -> "删";
            default -> "无";
        });

        String print = CompareResultPrint.print(list, aDefault);

        System.out.println(print);

    }

    @Test
    public void testSimpleBean() {

        User user = new User();
        user.setId(1L);
        user.setName("tom");
        user.setAge(18);
        user.setType((byte) 1);
        user.setType6(new String[]{"1", "2"});
        user.setType8(List.of(1));

        User user2 = new User();
        user2.setId(1L);
        user2.setName("jerry");
        user2.setAge(null);
        user2.setAddress("new ");

        BeanCompare compare = new BeanCompare();
        CompareOption aDefault = CompareOption.DEFAULT;
        aDefault.setOnlyNameAnnotation(false);

        List<CompareResult> compare1 = compare.compare(user, user2, aDefault);
        String print = CompareResultPrint.print(compare1, CompareOption.DEFAULT);

        System.out.println(print);


        List<CompareResult> compare2 = compare.compare(null, user, aDefault);
        String print2 = CompareResultPrint.print(compare2, CompareOption.DEFAULT);

        System.out.println(print2);

        // Map<Integer, String> c = new HashMap<>();
        // compare.compareCollection(c, user, aDefault);



    }
}