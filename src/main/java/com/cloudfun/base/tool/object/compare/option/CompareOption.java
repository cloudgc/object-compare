package com.cloudfun.base.tool.object.compare.option;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;

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
    private boolean ignoreNativeMethod = true;


    /**
     * compare only name annotation filed
     */
    private boolean onlyNameAnnotation = true;

    private NumberFormat numberFormat = getDefaultNumberFormat();

    private DateTimeFormatter dateTimeFormat = getDateTimeFormat("yyyy-MM-dd HH:mm:ss");

    private DateTimeFormatter dateFormat = getDateTimeFormat("yyyy-MM-dd");

    private DateTimeFormatter timeFormat = getDateTimeFormat("HH:mm:ss");


    public boolean getOnlyNameAnnotation() {
        return onlyNameAnnotation;
    }


    public void setOnlyNameAnnotation(boolean onlyNameAnnotation) {
        this.onlyNameAnnotation = onlyNameAnnotation;
    }

    public boolean getIgnoreNativeMethod() {
        return ignoreNativeMethod;
    }

    public void setIgnoreNativeMethod(boolean ignoreNativeMethod) {
        this.ignoreNativeMethod = ignoreNativeMethod;
    }

    public boolean isIgnoreNativeMethod() {
        return ignoreNativeMethod;
    }

    public boolean isOnlyNameAnnotation() {
        return onlyNameAnnotation;
    }

    public NumberFormat getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(NumberFormat numberFormat) {
        this.numberFormat = numberFormat;
    }

    public DateTimeFormatter getDateTimeFormat() {
        return dateTimeFormat;
    }

    public void setDateTimeFormat(DateTimeFormatter dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
    }

    public DateTimeFormatter getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateTimeFormatter dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DateTimeFormatter getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(DateTimeFormatter timeFormat) {
        this.timeFormat = timeFormat;
    }

    static NumberFormat getDefaultNumberFormat() {
        NumberFormat instance = NumberFormat.getInstance();
        instance.setRoundingMode(RoundingMode.HALF_UP);
        instance.setMaximumFractionDigits(2);
        return instance;
    }

    static DateTimeFormatter getDateTimeFormat(String format) {
        return DateTimeFormatter.ofPattern(format);
    }


}
