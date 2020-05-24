package com.hello.adminservice.controller.engine;


import com.hello.adminservice.util.Result;
import com.hello.common.dto.olis.Engine;
import com.hello.common.dto.olis.EngineType;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/11/10  11:06
 */
@Api(value = "发动机接口",description = "EngineController")
@RequestMapping(path = "/engine")
public interface EngineController {
    @ApiOperation(value="保存/更新发动机类型信息", notes="保存/更新发动机类型信息")
    @RequestMapping(path = "/engineType",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<EngineType> saveEngineTypes(@RequestBody @ApiParam(name = "发动机", value = "传入json格式", required = true) EngineType engineType);

    @ApiOperation(value="保存/更新发动机信息", notes="保存/更新发动机信息")
    @RequestMapping(path = "/engine",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Engine> saveEngine(@RequestBody @ApiParam(name = "发动机", value = "传入json格式", required = true) Engine engine);


    @ApiOperation(value = "分页获取发动机类型集合(完成)", notes = "根据查询条件来获取集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "engineTypeName", value = "发动机类型名", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/engineTypes", method = RequestMethod.GET)
    Result<Page<EngineType>> engineTypes(String engineTypeName, Integer pageNo);


    @ApiOperation(value = "分页获取发动机集合(完成)", notes = "根据查询条件来获取集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "engineName", value = "发动机名", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/engines", method = RequestMethod.GET)
    Result<Page<Engine>> engines(String engineName, Integer pageNo);


    @ApiOperation(value="删除发动机", notes="根据发动机的id来删除发动机")
    @ApiImplicitParam(name = "id", value = "发动机id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/engine/{id}",method = RequestMethod.DELETE)
    Result del(@PathVariable Long id);

    @ApiOperation(value = "通过年限id获取发动机类型集合(完成)", notes = "根据查询条件来获取集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "yearId", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/engineTypesByYearId/{id}", method = RequestMethod.GET)
    Result<List<EngineType>> engineTypes(@PathVariable Long id);
}
