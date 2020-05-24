package com.hello.adminservice.util;

import org.apache.commons.collections4.IteratorUtils;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 通用工具类
 * Created by hzh on 2018/7/4.
 */
public class CommonUtils {

    /**
     * 使用apache commons包转换Iterator为List
     *
     * @param i
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(Iterator<T> i) {
        return IteratorUtils.toList(i);
    }

    /**
     * String数组判断是否包含元素,简单循环比转换为list,set效率都高
     *
     * @param arr
     * @param targetValue
     * @return
     */
    public static boolean arrContains(String[] arr, String targetValue) {
        for (String s : arr) {
            if (s.equals(targetValue))
                return true;
        }
        return false;
    }

    /**
     * 返回今天的日期字符串,格式"yyyyMMdd"
     *
     * @return
     */
    public static String formatTodayStr() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(date);
    }

    /**
     * 返回uuid最后一段
     * @return
     */
    public static String partUuidStr() {
        String uuid = UUID.randomUUID().toString();
        String str = uuid.substring(uuid.lastIndexOf("-") + 1);
        return str;
    }


    /**
     * 昨天零点
     * dayOfff:天数的偏移量
     * 	-1：昨天
     * 0：今天
     * 1：后天
     * @return
     */
    public static Date yestoday(int dayOfff){
        Calendar calendar = Calendar. getInstance();
        calendar.setTime( new Date());
        calendar.set(Calendar. HOUR_OF_DAY, 0);
        calendar.set(Calendar. MINUTE, 0);
        calendar.set(Calendar. SECOND, 0);
        calendar.set(Calendar. MILLISECOND, 0);
        calendar.add(Calendar. DAY_OF_MONTH, dayOfff);
        return calendar.getTime();
    }

    /**
     * 数组转换为字符串
     * @param separator
     * @param arr
     * @return
     */
    public static String arraysToString(String separator,String [] arr){
        if(arr==null||arr.length<1)return null;
        StringBuffer sb = new StringBuffer();
        for (String s : arr) {
            sb.append(s+separator);
        }
        return  sb.substring(0,sb.length()-1).toString();
    }

    public static void main(String[] args) {

        System.out.println("sdfasfd".equals(null));
    }
    


}
