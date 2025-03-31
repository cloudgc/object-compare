package com.cloudfun.base.tool.object.compare.option;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/3/28 16:57
 */
public class CompareOption {


    /**
     * default option
     */
    public static final CompareOption DEFAULT = new CompareOption();


    /**
     * ignore native method
     * <p>
     * ignore from java native object method like getClass() or hashCode()
     */
    private Boolean ignoreNativeMethod = true;

    public Boolean getIgnoreNativeMethod() {
        return ignoreNativeMethod;
    }

    public void setIgnoreNativeMethod(Boolean ignoreNativeMethod) {
        this.ignoreNativeMethod = ignoreNativeMethod;
    }
}
