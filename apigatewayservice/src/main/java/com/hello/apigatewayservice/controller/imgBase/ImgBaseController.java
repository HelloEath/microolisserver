package com.hello.apigatewayservice.controller.imgBase;

import com.hello.apigatewayservice.util.Result;
import com.hello.common.dto.olis.ImgBase;
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
@Api(value = "图片基础数接口",description = "ImgBaseController")
@RequestMapping(path = "/imgBase")
public interface ImgBaseController {

    @ApiOperation(value="保存/更新信息", notes="保存/更新信息")
    @RequestMapping(path = "/imgBase",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<ImgBase> saveImgBase(@RequestBody @ApiParam(name = "图片基础", value = "传入json格式", required = true) ImgBase imgBase);



    @ApiOperation(value = "分页获取图片基础集合(完成)", notes = "根据查询条件来获取集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "materialName", value = "图片基础名称", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/imgBases", method = RequestMethod.GET)
    Result<Page<ImgBase>> imgBases(String materialName, Integer pageNo, Integer materialType);


    @ApiOperation(value = "获取全部图片基础集合(完成)", notes = "根据查询条件来获取集合")
    @RequestMapping(path = "/allImgBases", method = RequestMethod.GET)
    Result imgBases();

    @ApiOperation(value = "根据条件获取图片基础集合(完成)", notes = "根据查询条件来获取集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "materialName", value = "图片基础名称", dataType = "String", paramType = "query"),
    })
    @RequestMapping(path = "/imgBases-type", method = RequestMethod.GET)
    Result<List<ImgBase>> imgBases(String materialName, Integer materialType);


    @ApiOperation(value="删除图片基础数", notes="根据id来删除")
    @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long",paramType = "path")
    @RequestMapping(path = "/{id}",method = RequestMethod.DELETE)
    Result<String> del(@PathVariable Long id);
}
