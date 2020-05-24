package com.hello.common.dto;

import com.hello.common.entity.system.UploadFile;

import java.io.File;

/**
 * Created by hzh on 2018/7/14.
 */
public class DownloadFile {
    private File file;
    private UploadFile uploadFile;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
}
