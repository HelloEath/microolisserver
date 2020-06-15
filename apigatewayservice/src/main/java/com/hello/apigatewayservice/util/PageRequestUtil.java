package com.hello.apigatewayservice.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * 分页请求组装工具
 * Created by hzh on 2018/7/8.
 */
public class PageRequestUtil {
    public static int DEFAULT_PAGE_SIZE = 10;

    /**
     * 创建分页请求(系统默认页大小20和默认排序字段id)
     * @param pageNo
     * @return
     */
    public static PageRequest build(Integer pageNo){
        if(null == pageNo)pageNo = 1;
        Sort sort =  Sort.by(Sort.Direction.DESC,"id");
        return  PageRequest.of(pageNo-1, DEFAULT_PAGE_SIZE,sort);
    }

    /**
     * 创建分页请求,指定一页大小
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static PageRequest build(Integer pageNo, Integer pageSize){
        if(null == pageNo)pageNo = 1;
        Sort sort =  Sort.by(Sort.Direction.DESC,"id");
        return PageRequest.of(pageNo-1, pageSize,sort);
    }

    /**
     * 创建分页请求,指定页大小和排序字段
     * @param pageNo
     * @param pageSize
     * @param sortFiled
     * @return
     */
    public static PageRequest build(Integer pageNo, Integer pageSize, String sortFiled){
        if(null == pageNo)pageNo = 1;
        Sort sort =  Sort.by(Sort.Direction.DESC,sortFiled);
        return PageRequest.of(pageNo-1, pageSize,sort);
    }

    /**
     * 创建分页请求,指定页大小和排序字段
     * @param pageNo
     * @param pageSize
     * @param sortFiled
     * @return
     */
    public static PageRequest build(Integer pageNo, Integer pageSize, String direction, String... sortFiled){
        if(null == pageNo)pageNo = 1;
        Sort sort = null;
        if("desc".equals(direction)){
            sort =  Sort.by(Sort.Direction.DESC,sortFiled);
        }else{
            sort =  Sort.by(Sort.Direction.ASC,sortFiled);
        }
        return PageRequest.of(pageNo-1, pageSize,sort);
    }
}
