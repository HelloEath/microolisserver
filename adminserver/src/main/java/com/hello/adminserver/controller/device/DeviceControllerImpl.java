package com.hello.adminserver.controller.device;

import com.hello.adminserver.service.device.DeviceService;
import com.hello.common.dto.olis.Device;
import com.hello.common.util.PageRequestUtil;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public  Result<Device> isValiDataDevice(String deviceId,String systemType,String token) {
      return ResultUtil.success(deviceService.isValiDataDevice(deviceId,systemType,token));
    }
}
