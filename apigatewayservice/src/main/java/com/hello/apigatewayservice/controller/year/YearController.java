package com.hello.apigatewayservice.controller.year;


import com.hello.apigatewayservice.util.Result;
import com.hello.common.dto.olis.Region;
import com.hello.common.dto.olis.RegionPrizeManage;
import com.hello.common.dto.olis.Year;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/1/30  20:56
 */
@RequestMapping(path = "/year")
public interface YearController {
    @ApiOperation(value = "根据id获取地区(完成)", notes = "根据id获取地区")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long", paramType = "path")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    Result<Region> getRegion(@PathVariable Long id);

    @ApiOperation(value = "保存/更新年限(完成)", notes = "保存/更新年限")
    @RequestMapping(path = "/year", method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Year> save(@RequestBody @ApiParam(name = "year", value = "传入json格式", required = true) Year year);

    @ApiOperation(value = "分页获取年限集合(完成)", notes = "根据查询条件来获取年限集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "年限", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/years", method = RequestMethod.GET)
    Result<Page<Year>> years(String keyYearWords, Integer pageNo);



    @ApiOperation(value = "获取所有年限集合(完成)", notes = "根据查询条件来获取年限集合")
    @RequestMapping(path = "/allYears", method = RequestMethod.GET)
    Result<List<Year>> years();

    @ApiOperation(value="删除地区", notes="根据id来删除地区")
    @ApiImplicitParam(name = "id", value = "地区id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    Result<String> del(@PathVariable Long id);

    @ApiOperation(value = "保存/更新地区价格(完成)", notes = "保存/更新价格地区")
    @RequestMapping(path = "/regionPrize", method = {RequestMethod.POST, RequestMethod.PUT})
    Result<RegionPrizeManage> save(@RequestBody @ApiParam(name = "地区department", value = "传入json格式", required = true) RegionPrizeManage regionPrizeManage);

    @ApiOperation(value = "根据id获取所有年限列表", notes = "根据id获取所有年限列表")
    @RequestMapping(path = "/findAllById", method = {RequestMethod.GET,RequestMethod.POST})
    Result<List<Year>> findAllById(@RequestBody List<Long> yearIdList);

}
