package com.hello.apigatewayservice.controller.olis;

import com.hello.common.dto.olis.Olis;
import com.hello.apigatewayservice.util.Result;
import com.hello.common.entity.system.UploadFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/10/27  11:46
 */
@Api(value = "olis接口",description = "OlisController")
@RequestMapping(path = "/olis")
public interface OlisController {

    @ApiOperation(value="获取油品列表")
    @RequestMapping(path = "/oliss",method = {RequestMethod.GET})
    Result<Olis> oliss(Integer pageNo, String olisname);


    @ApiOperation(value="保存/更新油品信息", notes="保存/更新油品信息")
    @RequestMapping(path = "/olis",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Olis> olis(@RequestBody @ApiParam(name = "油品", value = "传入json格式", required = true) Olis olis);


    @ApiOperation(value="根据id保存/更新油品价格", notes="根据id保存/更新油品价格")
    @RequestMapping(path = "/olis-id",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<Olis> saveOlisById(@RequestBody Olis olis);

    @ApiOperation(value="删除油品信息")
    @RequestMapping(path = "/olis/{id}",method = {RequestMethod.DELETE})
    Result del(@PathVariable Long id);

    @ApiOperation(value ="上传油品图片")
    @RequestMapping(path = "/olisImage",method = {RequestMethod.POST})
    Result<UploadFile> uploadOlisImage(@RequestParam("file") MultipartFile file);




}
