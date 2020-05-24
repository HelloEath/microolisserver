package com.hello.adminservice.controller.engine;

import com.hello.adminservice.service.engine.EngineService;
import com.hello.adminservice.util.PageRequestUtil;
import com.hello.adminservice.util.Result;
import com.hello.adminservice.util.ResultUtil;
import com.hello.common.dto.olis.Engine;
import com.hello.common.dto.olis.EngineType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/11/10  11:06
 */
@RestController
public class EngineControllerImpl implements EngineController {
    @Autowired
    EngineService engineService;

    @Override
    public Result<EngineType> saveEngineTypes(@RequestBody EngineType engineType) {
        engineService.saveEnginType(engineType);
        return ResultUtil.success();
    }

    @Override
    public Result<Engine> saveEngine(@RequestBody Engine engine) {
        engineService.save(engine);
        return ResultUtil.success();
    }

    @Override
    public Result<Page<EngineType>> engineTypes(String engineTypeName, Integer pageNo) {
        return ResultUtil.success(engineService.searchType(PageRequestUtil.build(pageNo),engineTypeName));
    }

    @Override
    public Result<Page<Engine>> engines(String engineName, Integer pageNo) {
        return ResultUtil.success(engineService.search(PageRequestUtil.build(pageNo),engineName));
    }

    @Override
    public Result del(@PathVariable Long id) {
        engineService.del(id);
        return ResultUtil.success();
    }

    @Override
    public Result<List<EngineType>> engineTypes(@PathVariable Long id) {
        return ResultUtil.success(engineService.findByYear(id));
    }
}
