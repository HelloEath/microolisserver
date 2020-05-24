package com.hello.adminservice.controller.device;

import com.hello.adminservice.util.Result;
import com.hello.adminservice.util.ResultUtil;
import com.hello.common.dto.olis.Device;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/30  10:11
 */
@Api(value = "地区接口",description = "AreaController")
@RequestMapping(path = "/device")
public interface DeviceController {
    @ApiOperation(value = "保存/更新设备信息",notes ="保存/更新设备信息" )
    @RequestMapping(path = "/device",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Device> save(@RequestBody @ApiParam(name = "设备", value = "传入json格式", required = true) Device device);

    @ApiOperation(value = "分页获取设备集合(完成)", notes = "根据查询条件来获取设备集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "deviceCode", value = "机器码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deviceProxy", value = "地区编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deviceProxyNumber", value = "代理人", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "deviceRegionId", value = "地区", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "分页码", dataType = "Integer", paramType = "query"),

    })
    @RequestMapping(path = "/devices", method = RequestMethod.GET)
    Result<Page<Device>> devices(String deviceCode, String deviceProxy, String deviceProxyNumber, Integer deviceRegionId, Integer pageNo);


    @ApiOperation(value="删除设备", notes="根据设备的id来删除设备")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    Result del(@PathVariable Long id);

    @ApiOperation(value="获取设备授权信息", notes="根据设备码来获取设备授权")
    @ApiImplicitParam(name = "id", value = "设备码", required = true, dataType = "String",paramType = "path")
    @RequestMapping(path = "/devicePermission/{deviceCode}",method = RequestMethod.GET)
    Result<Device> getPermission(String deviceCode);


    @ApiOperation(value="验证设备授权信息", notes="根据设备码来设备授权")
    @RequestMapping(path = "/isValiDataDevice",method = {RequestMethod.GET})
     Object isValiDataDevice(String deviceId,String systemType,String token);
}
