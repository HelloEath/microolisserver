package com.hello.adminserver.controller.region;

import com.hello.common.dto.olis.Region;
import com.hello.common.dto.olis.RegionPrizeManage;
import com.hello.common.util.Result;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * 地区管理接口
 * Created by lxw on 2019/08/27.
 */
@Api(value = "地区管理接口", description = "RegionController,地区管理api")
@RequestMapping(path = "/region")
public interface RegionController {

    @ApiOperation(value = "根据id获取地区(完成)", notes = "根据id获取地区")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    Result<Region> getRegion(@PathVariable Long id);

    @ApiOperation(value = "根据id获取地区油品列表(完成)", notes = "根据id获取油品列表")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(path = "/olis/{id}", method = RequestMethod.GET)
    Result<RegionPrizeManage> getRegionOlis(@PathVariable Long id);

    @ApiOperation(value = "保存/更新地区(完成)", notes = "保存/更新地区")
    @RequestMapping(path = "/region", method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Region> save(@RequestBody @ApiParam(name = "地区department", value = "传入json格式", required = true) Region region);

    @ApiOperation(value = "分页获取地区集合(完成)", notes = "根据查询条件来获取地区集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "地区名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vCode", value = "地区编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "分页码", dataType = "Integer", paramType = "query")
    })
    @RequestMapping(path = "/regions", method = RequestMethod.GET)
    Result<Page<Region>> regions(String regionName, String vCode, Integer pageNo);

    @ApiOperation(value="删除地区", notes="根据id来删除地区")
    @ApiImplicitParam(name = "id", value = "地区id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    Result<String> del(@PathVariable Long id);

    @ApiOperation(value="删除地区油品", notes="根据油品id来删除地区油品")
    @ApiImplicitParam(name = "id", value = "地区id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/olis/{id}",method = RequestMethod.DELETE)
    Result<String> delPrize(@PathVariable Long id);

    @ApiOperation(value = "保存/更新地区价格(完成)", notes = "保存/更新价格地区")
    @RequestMapping(path = "/regionPrize", method = {RequestMethod.POST, RequestMethod.PUT})
    Result<RegionPrizeManage> save(@RequestBody @ApiParam(name = "地区department", value = "传入json格式", required = true) RegionPrizeManage regionPrizeManage);

    @ApiOperation(value = "获取所有地区", notes = "返回所有地区列表")
    @RequestMapping(path = "/getRegions", method = RequestMethod.GET)
    Result<List> getAllRegions();

    @ApiOperation(value = "根据类型获取所有地区", notes = "返回所有地区列表")
    @RequestMapping(path = "/findAllBySystemType", method = RequestMethod.GET)
    Result<List<Region>> findAllBySystemType(String systemType);
}
