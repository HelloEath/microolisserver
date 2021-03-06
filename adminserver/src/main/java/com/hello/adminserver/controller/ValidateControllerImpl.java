package com.hello.adminserver.controller;

import  com.hello.common.controller.common.BaseController;
import com.hello.adminserver.service.ValidateService;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by hzh on 2018/6/22.
 */
@RestController
public class ValidateControllerImpl extends BaseController implements ValidateController {
    @Autowired
    ValidateService validateService;

    @Override
    public Result<Boolean> uniqueUserName(String userName, Long oldId) {
        return ResultUtil.success(validateService.isUserNameUnique(userName,oldId));
    }

    @Override
    public Result<Boolean> uniqueRegion(String regionName, Long oldId) {
        return ResultUtil.success(validateService.isRegionUnique(regionName,oldId));

    }

    @Override
    public Result<Boolean> uniqueBrand(String brandName, Long oldId) {
        return ResultUtil.success(validateService.isBrandUnique(brandName,oldId));
    }

    @Override
    public Result<Boolean> uniqueEngineType(String engineTypeName , Long yearId) {
        return ResultUtil.success(validateService.isEngineTypeUnique(engineTypeName,yearId));
    }

    @Override
    public Result<Boolean> uniqueYear(String keyYearWords) {
        return ResultUtil.success(validateService.isYearUnique(keyYearWords));
    }
}
