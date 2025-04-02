package com.cloudfun.base.tool.object.compare.option;

import com.cloudfun.base.tool.object.compare.contants.CompareType;
import com.cloudfun.base.tool.object.compare.tool.BeanValueFormat;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

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

    /**
     * print log only value change
     */
    private boolean onlyPrintValueChange = true;

    private Map<Class<?>, BeanValueFormat> typeBeanValueFormat = Collections.emptyMap();

    private NumberFormat numberFormat = getDefaultNumberFormat();

    private DateTimeFormatter dateTimeFormat = getDateTimeFormat("yyyy-MM-dd HH:mm:ss");

    private DateTimeFormatter dateFormat = getDateTimeFormat("yyyy-MM-dd");

    private DateTimeFormatter timeFormat = getDateTimeFormat("HH:mm:ss");

    private String printFieldFormat = "{fieldName}[{compareType}]: {originValue:} -> {targetValue:}";

    private Function<CompareType, String> compareTypeFormat = Enum::toString;

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


    public Map<Class<?>, BeanValueFormat> getTypeBeanValueFormat() {
        return typeBeanValueFormat;
    }

    public void setTypeBeanValueFormat(Map<Class<?>, BeanValueFormat> typeBeanValueFormat) {
        this.typeBeanValueFormat = typeBeanValueFormat;
    }

    public boolean isOnlyPrintValueChange() {
        return onlyPrintValueChange;
    }

    public void setOnlyPrintValueChange(boolean onlyPrintValueChange) {
        this.onlyPrintValueChange = onlyPrintValueChange;
    }

    public String getPrintFieldFormat() {
        return printFieldFormat;
    }

    public void setPrintFieldFormat(String printFieldFormat) {
        this.printFieldFormat = printFieldFormat;
    }

    public Function<CompareType, String> getCompareTypeFormat() {
        return compareTypeFormat;
    }

    public void setCompareTypeFormat(Function<CompareType, String> compareTypeFormat) {
        this.compareTypeFormat = compareTypeFormat;
    }
}
