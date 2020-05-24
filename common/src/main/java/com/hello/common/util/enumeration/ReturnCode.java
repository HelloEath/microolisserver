package com.hello.common.util.enumeration;

/**
 * 自定义返回码
 * Created by hzh on 2018/7/6.
 */
public enum ReturnCode {
    SUCCESS("操作成功","0"),
    FAILED("操作失败","1"),
    WARNING("系统警告","2"),
    UNKOWN("未知异常", "50"),
    ERROR("系统错误", "51"),
    TIMEOUT("token不存在", "71");

    private String name;
    private String value;
    private String description = "服务器自定义返回状态码";

    ReturnCode(String name, String value) {
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
