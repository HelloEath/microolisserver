package com.hello.adminserver.controller.sae;

import com.hello.adminserver.service.saeDesc.SaeDescService;
import com.hello.common.dto.olis.SaeDesc;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/2/4  10:52
 */
@RestController
public class SaeControllerImpl implements SaeController{
    @Autowired
    private SaeDescService saeDescService;
    @Override
    public Result<SaeDesc> save(@RequestBody SaeDesc saeDesc) {
        saeDescService.save(saeDesc);
        return ResultUtil.success();
    }

    @Override
    public Result<List<SaeDesc>> SaeDescs(Long engineTypeId,Long threeId) {
        return ResultUtil.success(saeDescService.seach(engineTypeId,threeId));
    }
}
