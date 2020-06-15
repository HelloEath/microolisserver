package com.hello.adminservice.controller.olis;

import com.hello.common.dto.olis.Olis;
import com.hello.adminservice.service.FileService;
import com.hello.adminservice.service.olis.OlisService;
import com.hello.common.util.PageRequestUtil;
import com.hello.common.util.Result;
import com.hello.common.util.ResultUtil;
import com.hello.common.entity.system.UploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2019/10/27  11:50
 */
@RestController
public class OlisControllerImpl implements OlisController {
    @Autowired
    FileService fileService;
    @Autowired
    OlisService olisService;


    @Override
    public Result<Olis> oliss(Integer pageNo, String olisname) {
        Page<Olis> page=olisService.getOlisList(PageRequestUtil.build(pageNo),olisname);
        return ResultUtil.success(page);
    }

    @Override
    public Result<Olis> olis(@RequestBody Olis olis) {
        return ResultUtil.success(olisService.save(olis));
    }

    @Override
    public Result<Olis> saveOlisById(@RequestBody Olis olis) {
        olisService.saveById(olis);
        return ResultUtil.success();
    }

    @Override
    public Result del(@PathVariable Long id) {
        olisService.deleteOlisById(id);
        return ResultUtil.success();
    }

    @Override
    public Result<UploadFile> uploadOlisImage(MultipartFile file) {
        return ResultUtil.success(fileService.uploadOlisImage(file));
    }
}
