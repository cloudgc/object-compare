package com.cloudfun.base.tool.object.compare;

import com.cloudfun.base.tool.object.compare.tool.BeanInfoTool;
import org.junit.jupiter.api.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/28 16:48
 */
class ObjectCompareTest {


    @Test
    public void test1() throws IntrospectionException {

        BeanInfo beanInfo = BeanInfoTool.getBeanInfo(new User());

        System.out.println(beanInfo);


    }
}