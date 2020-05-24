package com.hello.common.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Controller返回结果封装
 * Created by hzh on 2018/6/21.
 */
@ApiModel(value="返回结果Result")
public class Result<T> {
    /**状态码*/
    @ApiModelProperty(value="状态码")
    private String code;
    /**提示信息*/
    @ApiModelProperty(value="提示信息")
    private String msg;
    /**数据对象*/
    @ApiModelProperty(value="返回的数据对象data")
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
