package com.hello.mobileservice.service.region;

import com.hello.common.dto.olis.Region;
import com.hello.common.util.Result;
import com.hello.mobileservice.service.year.YearServerHandler;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  21:00
 */
@FeignClient(value = "olis-server",path = "/region", fallbackFactory = RegionHandler.class)
public interface RegionService {

    @RequestMapping(value = "/findAllBySystemType",method = RequestMethod.GET)
    Result<List<Region>> findAllBySystemType(@RequestParam("systemType") String systemType);
}
