package com.cloudfun.base.tool.object.compare.bean;

import com.cloudfun.base.tool.object.compare.anno.Name;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/5/14 11:36
 */
public class SimpleBeanCollection {


    @Name(value = "nameType27", ignoreArraySort = true)
    private Integer[] type27;

    public Integer[] getType27() {
        return type27;
    }

    public void setType27(Integer[] type27) {
        this.type27 = type27;
    }

    public static SimpleBeanCollection createTargetBean() {
        SimpleBeanCollection simpleBean = new SimpleBeanCollection();
        simpleBean.setType27(new Integer[]{1, 2, 3, 1});
        return simpleBean;
    }

    public static SimpleBeanCollection createOriginBean() {
        SimpleBeanCollection simpleBean = new SimpleBeanCollection();
        simpleBean.setType27(new Integer[]{3, 2, 1, 1});
        return simpleBean;
    }
}
