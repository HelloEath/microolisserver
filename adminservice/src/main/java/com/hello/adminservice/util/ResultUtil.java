package com.hello.adminservice.util;

import com.hello.adminservice.util.enumeration.ReturnCode;

/**
 * 封装工具
 * Created by hzh on 2018/6/21.
 */
public class ResultUtil {
    /**
     * 操作成功(带返回数据)
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ReturnCode.SUCCESS.getValue());
        result.setMsg(ReturnCode.SUCCESS.getName());
        result.setData(object);
        return result;
    }

    /**
     * 操作成功(不带返回数据)
     * @param
     * @return
     */
    public static Result success() {
        return success(null);
    }


    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result error(Exception e) {
        Result result = new Result();
        result.setCode(ReturnCode.ERROR.getValue());
        result.setMsg(ReturnCode.ERROR.getName()+":"+e.getMessage()+" 异常类型:"+e.getClass().getName());
        return result;
    }
    
    public static Result success(String msg, Object object) {
        Result result = new Result();
        result.setCode(ReturnCode.SUCCESS.getValue());
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
    public static Result success(String msg,String code, Object object) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
    public static Result success(String msg,String code) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    /**
     * 操作失败(带返回数据)
     * @param object
     * @return
     */
    public static Result failed(Object object) {
    	Result result = new Result();
    	result.setCode(ReturnCode.FAILED.getValue());
    	result.setMsg(ReturnCode.FAILED.getName());
    	result.setData(object);
    	return result;
    }
    
    /**
     * 操作失败(不带返回数据)
     * @param
     * @return
     */
    public static Result failed() {
    	return failed(null);
    }
    
    /**
     * 操作失败(自定义错误信息，带返回数据)
     * @param msg
     * @param object
     * @return
     */
    public static Result failed(String msg, Object object) {
        Result result = new Result();
        result.setCode(ReturnCode.FAILED.getValue());
        result.setMsg(msg);
        result.setData(object);
        return result;
    }
    public static Result failed(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    /**
     * 操作失败(自定义错误信息，不带返回数据)
     * @param msg
     * @param object
     * @return
     */
    public static Result failed(String msg) {
    	return failed(msg, null);
    }
    
    /**
     * 系统警告(带返回数据)
     * @param object
     * @return
     */
    public static Result warning(Object object) {
    	Result result = new Result();
    	result.setCode(ReturnCode.WARNING.getValue());
    	result.setMsg(ReturnCode.WARNING.getName());
    	result.setData(object);
    	return result;
    }
    
    /**
     * 系统警告(不带返回数据)
     * @param
     * @return
     */
    public static Result warning() {
    	return warning(null);
    }
    
    /**
     * 系统警告(自定义警告信息，带返回数据)
     * @param msg
     * @param object
     * @return
     */
    public static Result warning(String msg, Object object) {
    	Result result = new Result();
    	result.setCode(ReturnCode.WARNING.getValue());
    	result.setMsg(msg);
    	result.setData(object);
    	return result;
    }
    
    /**
     * 系统警告(自定义错误信息，不带返回数据)
     * @param msg
     * @param object
     * @return
     */
    public static Result warning(String msg) {
    	return warning(msg, null);
    }
    
}
