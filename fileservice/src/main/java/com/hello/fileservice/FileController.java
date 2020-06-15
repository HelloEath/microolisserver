package com.hello.fileservice;

import com.hello.common.entity.system.UploadFile;
import com.hello.common.util.CommonUtils;
import com.hello.common.util.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/6/12  15:42
 * @desc:
 */
@RequestMapping(value = "/file")
public interface FileController {

    /**
     * 删除uploadfile,包括对应物理文件
     * @param id
     */
    void delUploadFile(Long id);


    /**
     * 上传保护油图片
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
     UploadFile upload(@RequestParam MultipartFile file, @RequestParam("path") String dirPath);

     UploadFile uploadFromWeb(String imgUrl, String dirPath);
}
