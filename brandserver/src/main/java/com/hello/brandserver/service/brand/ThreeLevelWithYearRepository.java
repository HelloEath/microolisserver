package com.hello.brandserver.service.brand;

import com.hello.common.dto.olis.ThreeLevelWithYear;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/23  18:44
 */
@FeignClient(value = "olis-server",path = "/brand", fallbackFactory = ThreeLevelWithYearHandler.class)
public interface ThreeLevelWithYearRepository {


    @RequestMapping(path = "/findByThreeId/{id}",method = RequestMethod.GET)
    Object findByThreeId(@RequestParam("id") Long id);

    @RequestMapping(path = "/findAllByYearIdAndThreeId/{id}",method = RequestMethod.GET)
    Object findAllByYearIdAndThreeId(@RequestParam("yearId") Long yearId,@RequestParam("threeId") Long threeId);
}
