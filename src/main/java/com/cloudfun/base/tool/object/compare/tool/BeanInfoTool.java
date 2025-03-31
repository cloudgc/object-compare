package com.cloudfun.base.tool.object.compare.tool;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/31 9:45
 */
public class BeanInfoTool {


    public static BeanInfo getBeanInfo(Object bean) throws IntrospectionException {


        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());

        return beanInfo;
    }

}
