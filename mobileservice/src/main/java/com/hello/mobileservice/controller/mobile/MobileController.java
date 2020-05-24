package com.hello.mobileservice.controller.mobile;

import com.hello.common.dto.olis.*;
import com.hello.common.util.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/21  10:14
 */
@Api(value = "移动端Api",description = "MobileController")
@RequestMapping(path = "/mobile")
public interface MobileController {


    @ApiOperation(value="获取所有品牌列表")
    @RequestMapping(path = "/brands",method = {RequestMethod.GET})
    Result<Map<String,List<Brand>>> brands(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @ApiOperation(value = "根据品牌id获取子车型集合(完成)", notes = "根据查询条件来获取子车型集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "品牌id", dataType = "Long", paramType = "path"),
    })
    @RequestMapping(path = "/oneCarTypes/{id}", method = RequestMethod.GET)
    Result<List<OneLevelCarType>> oneCars(@PathVariable Long id, HttpServletRequest httpServletRequest);

    @ApiOperation(value = "根据三级车型id获取年限集合(完成)", notes = "根据查询条件来获取年限集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "三级车型id", dataType = "Long", paramType = "path"),
    })
    @RequestMapping(path = "/year/{id}", method = RequestMethod.GET)
    Result<List<Year>> years(@PathVariable Long id, HttpServletRequest httpServletRequest);


    @ApiOperation(value = "根据年限id获取发动机类型集合(完成)", notes = "根据查询条件来获取发动机类型集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "年限id", dataType = "Long", paramType = "path"),
    })
    @RequestMapping(path = "/engineType", method = RequestMethod.GET)
    Result<List<ThreeLevelWithYear>> engineTypes(Long id, Long threeId, HttpServletRequest httpServletRequest);

    @ApiOperation(value = "根据三级车型id和发动机类型id获取发动机和用油信息(完成)", notes = "根据查询条件来获取发动机类型集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ThreeId", value = "三级车型id", dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "engineTypeId", value = "发动机类型id", dataType = "Long", paramType = "path"),
    })
    @RequestMapping(path = "/saeList", method = RequestMethod.GET)
    Result<List<SaeDesc>> saeList(Long threeId, Long engineTypeId, Long yearId, HttpServletRequest httpServletRequest);


    @ApiOperation(value = "获取所有地区", notes = "返回所有地区列表")
    @RequestMapping(path = "/getRegions", method = RequestMethod.GET)
    Result<List> aopGetAllRegions(HttpServletRequest httpServletRequest);

    @ApiOperation(value = "验证请求客户端是否存在", notes = "")
    @RequestMapping(path = "/deviceValidate", method = RequestMethod.POST)
    Result<Device> aopDeviceValidate(HttpServletRequest httpServletRequest);

    @ApiOperation(value = "保存设备信息",notes ="保存/更新设备信息" )
    @RequestMapping(path = "/device",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Device> aopSave(@RequestBody @ApiParam(name = "设备", value = "传入json格式", required = true) Device device);

}
