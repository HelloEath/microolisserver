package com.hello.adminservice.Exception;

/**
 * 业务异常
 * Created by hzh on 2018/7/6.
 */
public class ApplicationException extends RuntimeException{
    public ApplicationException(String message){
        super(message);
    }
}
