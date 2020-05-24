package com.hello.mobileservice.service.device;

import com.hello.common.dto.olis.Device;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  20:56
 */
@Component
public class DeviceServiceHandler implements FallbackFactory<Device> {
    @Override
    public Device create(Throwable cause) {
        System.out.print("获取设备信息失败");
        return null;
    }
}
