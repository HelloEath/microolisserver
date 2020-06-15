package com.hello.apigatewayservice.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 1.将数字转换成以万为单位或者以亿为单位，因为在前端数字太大显示有问题
 * 2.将数字转换为阿拉伯数字
 *
 * @author Hello_Earther(ShenYongJian)
 * @date 2018/12/5  11:18
 */
public class NumberUtils {
    private static final Double MILLION = 10000.0;
    private static final Double MILLIONS = 1000000.0;
    private static final Double BILLION = 100000000.0;
    private static final String MILLION_UNIT = "万";
    private static final String BILLION_UNIT = "亿";
    private static char[] numArry = {' ', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
    private static String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿"};
    public static String amountConversion(double amount) {
        //最终返回的结果值
        String result = String.valueOf(amount);
        //四舍五入后的值
        double value = 0;
        //转换后的值
        double tempValue = 0;
        //余数
        double remainder = 0;

        //金额大于1百万小于1亿
        if (amount > MILLIONS && amount < BILLION) {
            tempValue = amount / MILLION;
            remainder = amount % MILLION;
            //log.info("tempValue=" + tempValue + ", remainder=" + remainder);

            //余数小于5000则不进行四舍五入
            if (remainder < (MILLION / 2)) {
                value = formatNumber(tempValue, 2, false);
            } else {
                value = formatNumber(tempValue, 2, true);
            }
            //如果值刚好是10000万，则要变成1亿
            if (value == MILLION) {
                result = zeroFill(value / MILLION) + BILLION_UNIT;
            } else {
                result = zeroFill(value) + MILLION_UNIT;
            }
        }
        //金额大于1亿
        else if (amount > BILLION) {
            tempValue = amount / BILLION;
            remainder = amount % BILLION;
            ///log.info("tempValue=" + tempValue + ", remainder=" + remainder);

            //余数小于50000000则不进行四舍五入
            if (remainder < (BILLION / 2)) {
                value = formatNumber(tempValue, 2, false);
            } else {
                value = formatNumber(tempValue, 2, true);
            }
            result = zeroFill(value) + BILLION_UNIT;
        } else {
            result = zeroFill(amount);
        }
        // log.info("result=" + result);
        return result;
    }


    /**
     * 对数字进行四舍五入，保留2位小数
     *
     * @param number   要四舍五入的数字
     * @param decimal  保留的小数点数
     * @param rounding 是否四舍五入
     * @return
     * @author
     * @version 1.00.00
     * @date 2018年1月18日
     */
    public static Double formatNumber(double number, int decimal, boolean rounding) {
        BigDecimal bigDecimal = new BigDecimal(number);

        if (rounding) {
            return bigDecimal.setScale(decimal, RoundingMode.HALF_UP).doubleValue();
        } else {
            return bigDecimal.setScale(decimal, RoundingMode.DOWN).doubleValue();
        }
    }

    /**
     * 对四舍五入的数据进行补0显示，即显示.00
     *
     * @return
     * @author
     * @version 1.00.00
     * @date 2018年1月23日
     */
    public static String zeroFill(double number) {
        DecimalFormat df = new DecimalFormat();//保留两位小数
        BigDecimal bg = new BigDecimal(number);
        String value = String.valueOf(df.format(bg));

       /* if(value.indexOf(".")<0){
            value = value + ".00";
        }else{
            String decimalValue = value.substring(value.indexOf(".")+1);

            if(decimalValue.length()<2){
                value = value + "0";
            }
        }*/
        return value;
    }

    /**
     * 对数字（如 1,2,3，，）转换为阿拉伯数字（一，二，三...）
     *
     * @return
     * @author
     * @version 1.00.00
     * @date 2018年1月23日
     */
    public static String formatNumToString(int num) {
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = units[(len - 1) - i];
            if (isZero) {
                if ('0' == val[i - 1]) {
                    continue;
                } else {
                    sb.append(numArry[n]);
                }
            } else {
                sb.append(numArry[n]);
                sb.append(unit);
            }

        }
        return sb.toString();
    }

}
