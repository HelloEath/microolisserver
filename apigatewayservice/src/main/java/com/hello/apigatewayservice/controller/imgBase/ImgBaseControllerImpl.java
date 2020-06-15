package com.hello.apigatewayservice.controller.imgBase;

import com.hello.common.dto.olis.ImgBase;
import com.hello.apigatewayservice.service.imgBase.ImgBaseService;
import com.hello.apigatewayservice.util.PageRequestUtil;
import com.hello.apigatewayservice.util.Result;
import com.hello.apigatewayservice.util.ResultUtil;
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
public class ImgBaseControllerImpl implements ImgBaseController {
    @Autowired
    ImgBaseService imgBaseService;

    @Override
    public Result<ImgBase> saveImgBase(@RequestBody ImgBase imgBase) {
        imgBaseService.save(imgBase);
        return ResultUtil.success();
    }

    @Override
    public Result<Page<ImgBase>> imgBases(String materialName, Integer pageNo, Integer materialType) {
        return ResultUtil.success(imgBaseService.search(PageRequestUtil.build(pageNo),materialName,materialType));
    }

    @Override
    public Result<List<ImgBase>> imgBases() {
        return ResultUtil.success(imgBaseService.findAll());
    }

    @Override
    public Result<List<ImgBase>> imgBases(String materialName, Integer materialType) {
        return ResultUtil.success(imgBaseService.search(materialName,materialType));
    }

    @Override
    public Result<String> del(@PathVariable Long id) {
        imgBaseService.del(id);
        return ResultUtil.success();
    }
}
