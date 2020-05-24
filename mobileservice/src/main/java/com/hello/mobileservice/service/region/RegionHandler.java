package com.hello.mobileservice.service.region;

import com.hello.common.dto.olis.Region;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  21:00
 */
@Component
public class RegionHandler implements FallbackFactory<Region> {
    @Override
    public Region create(Throwable cause) {
        System.out.print("获取地区失败");
        return null;
    }
}
