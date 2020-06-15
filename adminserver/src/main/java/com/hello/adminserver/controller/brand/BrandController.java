package com.hello.adminserver.controller.brand;


import com.hello.common.dto.olis.*;
import com.hello.common.entity.system.UploadFile;
import com.hello.common.util.Result;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/11/10  10:53
 */
@Api(value = "品牌接口",description = "BrandController")
@RequestMapping(path = "/brand")
public interface BrandController {
    @ApiOperation(value="保存/更新品牌信息", notes="保存/更新品牌信息")
    @RequestMapping(path = "/brand",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Brand> save(@RequestBody @ApiParam(name = "品牌", value = "传入json格式", required = true) Brand brand);

    @ApiOperation(value="保存/更新一级车型信息", notes="保存/更新一级车型信息")
    @RequestMapping(path = "/oneCarType",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<OneLevelCarType> saveOne(@RequestBody @ApiParam(name = "车型", value = "传入json格式", required = true) OneLevelCarType oneLevelCarType);

    @ApiOperation(value="保存/更新二级车型信息", notes="保存/更新二级车型信息")
    @RequestMapping(path = "/twoCarType",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<TwoLevelCarType> saveTwo(@RequestBody @ApiParam(name = "车型", value = "传入json格式", required = true) TwoLevelCarType twoLevelCarType);


    @ApiOperation(value="保存/更新三级车型信息", notes="保存/更新三级车型信息")
    @RequestMapping(path = "/threeCarType",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<ThreeLevelCarType> saveThree(@RequestBody @ApiParam(name = "车型", value = "传入json格式", required = true) ThreeLevelCarType threeLevelCarType);


    @ApiOperation(value ="上传品牌图片")
    @RequestMapping(path = "/brandLogo",method = {RequestMethod.POST})
    Result<UploadFile> uploadBrandImage(@RequestParam("file") MultipartFile file);

    @ApiOperation(value = "分页获取品牌集合(完成)", notes = "根据查询条件来获取地品牌集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandName", value = "地区名称", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "vCode", value = "地区编号", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageNo", value = "分页码", dataType = "Integer", paramType = "query")
    })
    @RequestMapping(path = "/brands", method = RequestMethod.GET)
    Result<Page<Brand>> brands(String brandName, String vCode, Integer pageNo);

    @ApiOperation(value = "分页获取所有品牌集合(完成)", notes = "根据查询条件来获取地品牌集合")
    @ApiImplicitParams({})
    @RequestMapping(path = "/allBrands", method = RequestMethod.GET)
    Result<List<Brand>> brands();

    @ApiOperation(value = "分页获取一级车型集合(完成)", notes = "根据查询条件来获取一级车型集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carTypeName", value = "名称", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/oneCarTypes", method = RequestMethod.GET)
    Result<Page<OneLevelCarType>> oneCars(String carTypeName, Integer pageNo);


    @ApiOperation(value = "分页获取二级车型集合(完成)", notes = "根据查询条件来获取二级车型集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carTypeName", value = "名称", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/twoCarTypes", method = RequestMethod.GET)
    Result<Page<TwoLevelCarType>> twoCars(String carTypeName, Integer pageNo);


    @ApiOperation(value = "分页获取三级车型集合(完成)", notes = "根据查询条件来获取三级车型集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carTypeName", value = "名称", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/threeCarTypes", method = RequestMethod.GET)
    Result<Page<ThreeLevelCarType>> threeCars(String carTypeName, Integer pageNo);


    @ApiOperation(value = "根据品牌id获取一级级车型集合(完成)", notes = "根据查询条件来获取一级车型集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "carTypeName", value = "名称", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/oneCarTypes/{id}", method = RequestMethod.GET)
    Result<List<OneLevelCarType>> oneCars(@PathVariable Long id);


    @ApiOperation(value = "根据一级车型id获取二级级车型集合(完成)", notes = "根据查询条件来获取二级车型集合")
    @ApiImplicitParams({})
    @RequestMapping(path = "/twoCarTypes/{id}", method = RequestMethod.GET)
    Result<List<TwoLevelCarType>> twoCars(@PathVariable Long id);

    @ApiOperation(value = "根据二级车型id获取三级级车型集合(完成)", notes = "根据查询条件来获取二级车型集合")
    @ApiImplicitParams({})
    @RequestMapping(path = "/threeCarTypes/{id}", method = RequestMethod.GET)
    Result<List<ThreeLevelCarType>> threeCars(@PathVariable Long id);

    @ApiOperation(value="删除品牌", notes="根据id来删除品牌")
    @ApiImplicitParam(name = "id", value = "品牌id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    Result<String> del(@PathVariable Long id);


    @ApiOperation(value="根据系统类型获取品牌信息", notes="根据系统类型获取品牌信息")
    @ApiImplicitParam(name = "systemType", value = "systemType", required = true, dataType = "String")
    @RequestMapping(path = "/findAllBrandBySystemType",method = {RequestMethod.POST,RequestMethod.GET})
    Result<List<Brand>> findAllBrandBySystemType(@RequestParam String systemType) ;

    @ApiOperation(value="根据品牌id获取一级子车型信息", notes="根据品牌id获取一级子车型信息")
    @ApiImplicitParam(name = "品牌id", value = "id", required = true, dataType = "String",paramType = "path")
    @RequestMapping(path = "/searchOneCarAllByBrandId/{id}",method = {RequestMethod.POST,RequestMethod.GET})
    Result<List<OneLevelCarType>> searchOneCarAllByBrandId(@PathVariable Long id) ;


    @ApiOperation(value="根据一级车型id获取二级子车型信息", notes="根据一级车型id获取二级子车型信息")
    @ApiImplicitParam(name = "一级车型id", value = "id", required = true, dataType = "String",paramType = "path")
    @RequestMapping(path = "/searchTwoCarAllByOneId/{id}",method = RequestMethod.GET)
    Result<List<TwoLevelCarType>> searchTwoCarAllByOneId(@PathVariable Long id) ;

    @ApiOperation(value="根据二级车型id获取三级子车型信息", notes="根据二级车型id获取三级子车型信息")
    @ApiImplicitParam(name = "二级车型id", value = "id", required = true, dataType = "String",paramType = "path")
    @RequestMapping(path = "/searchThreeCarAllByTwoId/{id}",method = RequestMethod.GET)
    Result<List<ThreeLevelCarType>> searchThreeCarAllByTwoId(@PathVariable Long id) ;

    @RequestMapping(path = "/findByThreeId/{id}",method = RequestMethod.GET)
    Result<List<ThreeLevelWithYear>> findByThreeId(@PathVariable Long id);

    @RequestMapping(path = "/findAllByYearIdAndThreeId",method ={RequestMethod.GET,RequestMethod.POST})
    Result<List<ThreeLevelWithYear>> findAllByYearIdAndThreeId(Long yearId, Long threeId);
}
