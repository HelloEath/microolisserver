package com.hello.adminservice.util.enumeration;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/4/24  20:43
 */
public enum DevicePerssionCode {
    AUTHENTICATED("设备已认证","0"),
    UNAUTHENTICATIED("设备未认证","1"),
    UNKOWN("设备不存在", "-1"),
    EXIST("设备存在", "2");

    private String name;
    private String value;
    private String description = "设备权限状态吗";

    DevicePerssionCode(String name, String value) {
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
