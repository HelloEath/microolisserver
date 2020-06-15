package com.hello.apigatewayservice.util.enumeration;

/**
 * 系统用户类型表标识
 * Created by lxw on 2019/09/17.
 */
public enum UserType {
    P1SVC("p1svc用户","0"),
    SZHYX("数字化营销用户","1"),
    P2("P2用户","2");

    private String name;
    private String value;
    private String description = "一些通用常量枚举";

    UserType(String name,String value){
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
