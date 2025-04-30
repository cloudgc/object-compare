package com.cloudfun.base.tool.object.compare.tool;

import com.cloudfun.base.tool.object.compare.bean.CompareResult;
import com.cloudfun.base.tool.object.compare.contants.CompareType;
import com.cloudfun.base.tool.object.compare.exception.CompareException;
import com.cloudfun.base.tool.object.compare.option.CompareOption;

import java.util.List;

/**
 * @author cloudgc
 * @apiNote
 * @since 2025/4/2 14:40
 */
public class CompareResultPrint {

    private static final String DEFAULT_EMPTY_STR = "";


    public static String print(List<CompareResult> compareResultList, CompareOption option) {
        if (compareResultList == null || compareResultList.isEmpty()) {
            return DEFAULT_EMPTY_STR;
        }
        String printFieldFormat = option.getPrintFieldFormat();
        if (!printFieldFormat.contains(":")) {
            throw new CompareException("printFieldFormat must have `:` split mark");
        }


        StringBuilder sb = new StringBuilder();

        innerPrint(compareResultList, option, sb, DEFAULT_EMPTY_STR, DEFAULT_EMPTY_STR);

        return sb.toString();

    }

    private static void innerPrint(List<CompareResult> compareResultList, CompareOption option, StringBuilder sb,
                                   String prefix, String firstPrefix) {

        boolean first = true;

        for (CompareResult result : compareResultList) {

            if (option.isOnlyPrintValueChange() && result.getCompareType() == CompareType.NONE) {
                continue;
            }


            String printFieldFormat = option.getPrintFieldFormat();
            int splitIndex = printFieldFormat.indexOf(":");
            String prefixPart = printFieldFormat.substring(0, splitIndex);
            String suffixPart = printFieldFormat.substring(splitIndex + 1);

            String prefixPartStr = replaceValue(result, prefixPart, option);
            String suffixPartStr = replaceValue(result, suffixPart, option);


            if (first && !firstPrefix.isEmpty()) {
                sb.append(firstPrefix);
                first = false;
            }else{
                sb.append(prefix);
            }
            sb.append(prefixPartStr).append(":");
            if (result.getChildren() == null || result.getChildren().isEmpty()) {
                sb.append(suffixPartStr).append("\n");
            } else {
                sb.append("\n");
                if (result.isCollection()) {
                    innerPrint(result.getChildren(), option, sb, prefix  +"    ", "  - ");
                } else {
                    innerPrint(result.getChildren(), option, sb, prefix +"  ", DEFAULT_EMPTY_STR);
                }
            }
        }
    }

    private static String replaceValue(CompareResult result, String strPart, CompareOption option) {
        String compareType = option.getCompareTypeFormat().apply(result.getCompareType());
        strPart = strPart.replace("{fieldName}", ifNull(result.getFieldName()));
        strPart = strPart.replace("{compareType}", compareType);
        strPart = strPart.replace("{originValue}", ifNull(result.getOriginValue()));
        strPart = strPart.replace("{targetValue}", ifNull(result.getTargetValue()));

        if (strPart.contains("{originValue:")) {
            strPart = replatString(strPart, "{originValue:", result.getOriginValue());
        }
        if (strPart.contains("{targetValue:")) {
            strPart = replatString(strPart, "{targetValue:", result.getTargetValue());
        }
        return strPart;
    }


    private static String replatString(String formatString, String replatString, String value) {
        int start = formatString.indexOf(replatString);
        int end = formatString.indexOf("}", start);
        if (end <= start) {
            end = start;
        }
        String defaultStr = formatString.substring(start + replatString.length(), end);
        return formatString.replace(replatString + defaultStr + "}", value == null ? defaultStr : value);


    }


    private static String ifNull(String value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return value;
    }


    private static String ifNull(String value) {
        return ifNull(value, DEFAULT_EMPTY_STR);
    }


}
