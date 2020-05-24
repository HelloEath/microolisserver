package com.hello.mobileservice.service.saesc;

import com.hello.common.dto.olis.SaeDesc;
import com.hello.common.util.Result;
import com.hello.mobileservice.service.year.YearServerHandler;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  21:02
 */
@FeignClient(value = "olis-server",path = "/SaeDesc", fallbackFactory = SaeDescServiceHandler.class)
public interface SaeDescService {

    @RequestMapping(value = "/SaeDescs-engineType",method = RequestMethod.GET)
    Result<List<SaeDesc>> findAllByEngineTypeIdAndThreeId(@RequestParam("engineTypeId") Long engineTypeId, @RequestParam("threeId") Long threeId);
}
