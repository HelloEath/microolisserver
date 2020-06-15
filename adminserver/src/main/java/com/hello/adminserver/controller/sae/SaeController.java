package com.hello.adminserver.controller.sae;

import com.hello.common.dto.olis.SaeDesc;
import com.hello.common.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/4  10:52
 */
@RequestMapping(path = "/SaeDesc")
public interface SaeController {

    @ApiOperation(value="保存/更新saeDesc", notes="保存/更新saeDesc")
    @RequestMapping(path = "/SaeDesc",method = {RequestMethod.POST, RequestMethod.PUT})
    Result<SaeDesc> save(@RequestBody @ApiParam(name = "saeDesc", value = "传入json格式", required = true) SaeDesc saeDesc);


    @ApiOperation(value = "根据发动机类型id获取saeDesc类型集合(完成)", notes = "根据查询条件来获取集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "engineTypId", value = "发动机类型id", dataType = "long", paramType = "query"),
    })
    @RequestMapping(path = "/SaeDescs-engineType", method = RequestMethod.GET)
    Result<List<SaeDesc>> SaeDescs(Long engineTypeId, Long threeId);



}
