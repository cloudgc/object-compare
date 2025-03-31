package com.cloudfun.base.tool.object.compare;

import com.cloudfun.base.tool.object.compare.bean.CompareResult;
import com.cloudfun.base.tool.object.compare.exception.CompareException;
import com.cloudfun.base.tool.object.compare.option.CompareOption;

import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * @apiNote
 * @since 2025/3/28 11:26
 */
public class ObjectCompare {


    /**
     * simple bean compare
     * <p>
     * only support simple bean compare, not support collection or array
     *
     * @param origin origin compare object
     * @param target target compare object
     * @param option compare option
     * @return field change value list
     * @throws CompareException parse object error
     */
    public static List<CompareResult> simpleBean(Object origin, Object target, CompareOption option){
        if (origin == null && target == null) {
            return Collections.emptyList();
        }








        return Collections.emptyList();
    }


    /**
     * simple bean compare
     * <p>
     * only support simple bean compare, not support collection or array
     * use CompareOption. DEFAULT as default option
     *
     * @param origin origin compare object
     * @param target target compare object
     * @return field change value list
     * @throws CompareException parse object error
     */
    public static List<CompareResult> simpleBean(Object origin, Object target){
        return simpleBean(origin, target, CompareOption.DEFAULT);
    }


}
