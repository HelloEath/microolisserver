package com.hello.adminservice.util.enumeration;

/**
 * 一些通用常量
 * Created by hzh on 2018/7/6.
 */

public enum Commons {
    YES("是","1"),
    NO("否","0"),
    ENABLED("激活","0"),
    DISABLED("禁用","1");

    private String name;
    private String value;
    private String description = "一些通用常量枚举";

    Commons(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public int getIntegerValue(){
        return  Integer.valueOf(value);
    }

    public String getDescription() {
        return description;
    }
}
