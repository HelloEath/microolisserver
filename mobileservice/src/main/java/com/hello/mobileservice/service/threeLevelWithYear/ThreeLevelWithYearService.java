package com.hello.mobileservice.service.threeLevelWithYear;

import com.hello.common.dto.olis.ThreeLevelWithYear;
import com.hello.common.util.Result;
import com.hello.mobileservice.service.brand.BrandServerHandler;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  20:51
 */
@FeignClient(value = "olis-server",path = "/brand", fallbackFactory = ThreeLevelWithYearServiceHandler.class)
public interface ThreeLevelWithYearService {

    @RequestMapping(value = "/findByThreeId/{id}",method = RequestMethod.GET)
    Result<List<ThreeLevelWithYear>> findByThreeId(@PathVariable("id") Long id);

    @RequestMapping(value = "/findAllByYearIdAndThreeId",method = RequestMethod.GET)
    Result<List<ThreeLevelWithYear>> findAllByYearIdAndThreeId(@RequestParam("id") Long id,@RequestParam("threeId") Long threeId);
}
