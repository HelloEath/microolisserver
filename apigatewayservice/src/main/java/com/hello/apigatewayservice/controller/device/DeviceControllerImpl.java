package com.hello.apigatewayservice.controller.device;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hello.apigatewayservice.service.device.DeviceService;
import com.hello.apigatewayservice.util.DateUtil;
import com.hello.apigatewayservice.util.PageRequestUtil;
import com.hello.apigatewayservice.util.Result;
import com.hello.apigatewayservice.util.ResultUtil;
import com.hello.apigatewayservice.util.enumeration.DevicePerssionCode;
import com.hello.apigatewayservice.util.enumeration.ReturnCode;
import com.hello.common.dto.olis.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/30  10:11
 */
@RestController
public class DeviceControllerImpl implements DeviceController {
    @Autowired
    DeviceService deviceService;
    @Override
    public Result<Device> save(@RequestBody Device device) {
        return deviceService.save(device);
    }

    @Override
    public Result<Page<Device>> devices(String deviceCode, String deviceProxy, String deviceProxyNumber, Integer deviceRegionId, Integer pageNo) {
        return ResultUtil.success(deviceService.search(deviceCode,deviceProxy,deviceProxyNumber,deviceRegionId, PageRequestUtil.build(pageNo)));
    }

    @Override
    public Result del(@PathVariable Long id) {
        deviceService.delDevice(id);
        return ResultUtil.success();
    }

    @Override
    public Result<Device> getPermission(@PathVariable String deviceCode) {
        return ResultUtil.success(deviceService.getDevicePermission(deviceCode));
    }


    @Override
    public Object isValiDataDevice(String deviceId,String systemType,String token) {
      return ResultUtil.success(deviceService.isValiDataDevice(deviceId,systemType,token));
    }
}
