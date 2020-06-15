package com.hello.adminserver.controller;


import com.hello.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by hzh on 2018/6/1.
 */
@Api(value = "验证接口",description = "ValidateController,唯一性验证等需要服务端远程数据验证api")
@RequestMapping(path = "/validate")
public interface ValidateController {

    @ApiOperation(value="获取用户唯一userName", notes="根据User的userName来获取用户唯一userName,会根据oldId排除当前用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户userName", required = true, dataType = "String"),
            @ApiImplicitParam(name = "oldId", value = "当前用户id", required = true, dataType = "Long")
    })
    @RequestMapping(path = "/uniqueUserChineseName",method = RequestMethod.GET)
    Result<Boolean> uniqueUserName(String userName, Long oldId) ;

    @ApiOperation(value="获取唯一地区", notes="根据地区名验证唯一地区")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "regionName", value = "地区Name", required = true, dataType = "String"),
            @ApiImplicitParam(name = "regionId", value = "当前地区id", required = true, dataType = "Long")
    })
    @RequestMapping(path = "/uniqueRegion",method = RequestMethod.GET)
    Result<Boolean> uniqueRegion(String regionName, Long oldId) ;

    @ApiOperation(value="获取唯一品牌", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandName", value = "品牌Name", required = true, dataType = "String"),
            @ApiImplicitParam(name = "brandId", value = "当前品牌id", required = true, dataType = "Long")
    })
    @RequestMapping(path = "/uniqueBrand",method = RequestMethod.GET)
    Result<Boolean> uniqueBrand(String brandName, Long oldId) ;

    @ApiOperation(value="获取唯一发动机类型", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "engineTypeName", value = "发动机类型名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "yearId", value = "年限id", required = true, dataType = "Long")
    })
    @RequestMapping(path = "/uniqueEngineType",method = RequestMethod.GET)
    Result<Boolean> uniqueEngineType(String engineTypeName, Long yearId) ;


    @ApiOperation(value="获取唯一年限", notes="")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyYearWords", value = "年限关键字", required = true, dataType = "String"),

    })
    @RequestMapping(path = "/uniqueYear",method = RequestMethod.GET)
    Result<Boolean> uniqueYear(String keyYearWords) ;
}
