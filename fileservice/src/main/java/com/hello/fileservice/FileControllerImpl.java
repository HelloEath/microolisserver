package com.hello.fileservice;

import com.hello.common.entity.system.UploadFile;
import com.hello.common.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/6/12  15:43
 * @desc:
 */
@RestController
public class FileControllerImpl implements FileController {
    @Autowired
    FileService fileService;
    @Override
    public void delUploadFile(@PathVariable Long id) {
        fileService.delUploadFile(id);
    }

    @Override
    public UploadFile upload(@RequestPart(value = "file") MultipartFile file, String dirPath) {
        return fileService.upload(file,dirPath);
    }


    @Override
    public UploadFile uploadFromWeb(String imgUrl, String dirPath) {
        return fileService.uploadFromWeb(imgUrl,dirPath);
    }
}
