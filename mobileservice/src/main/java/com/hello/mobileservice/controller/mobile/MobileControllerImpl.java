package com.hello.mobileservice.controller.mobile;

import com.hello.common.dto.olis.*;

import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import com.hello.mobileservice.service.mobile.MobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/21  10:15
 */
@RestController
public class MobileControllerImpl implements MobileController {

    @Autowired
    private MobileService mobileService;

    @Override
    public Result<Map<String, List<Brand>>> brands(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String systemType = httpServletRequest.getHeader("systemType");
        return ResultUtil.success(mobileService.brands(systemType));
    }

    @Override
    public Result<List<OneLevelCarType>> oneCars(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return ResultUtil.success(mobileService.findCarTypes(id));

    }

    @Override
    public Result<List<Year>> years(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        return ResultUtil.success(mobileService.yearList(id));

    }

    @Override
    public Result<List<ThreeLevelWithYear>> engineTypes(Long id, Long threeId, HttpServletRequest httpServletRequest) {
        return ResultUtil.success(mobileService.engineTypeList(id, threeId));

    }

    @Override
    public Result<List<SaeDesc>> saeList(Long threeId, Long engineTypeId, Long yearId, HttpServletRequest httpServletRequest) {
        return ResultUtil.success(mobileService.saeList(threeId, engineTypeId, yearId));

    }

    @Override
    public Result<List> aopGetAllRegions(HttpServletRequest httpServletRequest) {
        List<Region> regionList = mobileService.findAllRegion(httpServletRequest);
        Result<List> result = new Result<>();
        result.setData(regionList);
        result.setMsg("查询结果条目数:" + regionList.size());
        return result;
    }

    @Override
    public Result<Device> aopDeviceValidate(HttpServletRequest httpServletRequest) {
        return ResultUtil.success(mobileService.deviceValidata(httpServletRequest));
    }

    @Override
    public Result<Device> aopSave(@RequestBody Device device) {
        return mobileService.saveDevice(device);
    }

}
