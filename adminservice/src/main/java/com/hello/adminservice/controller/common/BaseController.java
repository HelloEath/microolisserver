package com.hello.adminservice.controller.common;

import com.hello.adminservice.util.Result;
import com.hello.adminservice.util.ResultUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础控制器,用于统一处理异常等等
 * Created by hzh on 2018/7/6.
 */
public class BaseController {
    /**
     * 基于@ExceptionHandler异常处理
     */
    @ExceptionHandler
    @ResponseBody
    public Result handleAndReturnData(Exception ex) {

//        if(ex instanceof BusinessException) {
//            BusinessException e = (BusinessException)ex;
//            data.put("code", e.getCode());
//        }
        if (ex instanceof DataIntegrityViolationException) {
           // map.put("msg", "该角色尚有关联的资源或用户，删除失败!");
        }
        ex.printStackTrace();
        return ResultUtil.error(ex);
    }

}
