package com.hello.common.dto;

import java.util.Map;

/**
 * 用于向api文档输出服务端全局枚举
 * Created by hzh on 2018/7/7.
 */
public class Enum4show {
    private String enumName;
    private Map<String,String> map;

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
