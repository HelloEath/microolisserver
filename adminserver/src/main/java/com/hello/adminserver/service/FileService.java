package com.hello.adminserver.service;

import com.hello.adminserver.config.AppConfig;
import com.hello.adminserver.repository.UploadFileRepository;
import com.hello.common.dto.DownloadFile;
import com.hello.common.entity.system.UploadFile;
import com.hello.common.util.CommonUtils;
import com.hello.common.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by hzh on 2018/7/13.
 */
@Transactional
@Service
public class FileService {
    @Resource
    AppConfig appConfig;
    @Autowired
    UploadFileRepository uploadFileRepository;
    //@Autowired
    //AttachmentRepository attachmentRepository;

    /**
     * 通过code获取uploadfile
     * @param code
     * @return
     */
    public UploadFile get(String code){
        return  uploadFileRepository.findByFileCode(code);
    }

    /**
     * 获取下载路径
     * @param code
     * @return
     */
    public String url(String code) throws UnsupportedEncodingException{
        ///temp/20180716/18b8d683d0bc.图片文件 .jpg
       UploadFile uploadFile = uploadFileRepository.findByFileCode(code);
       String path = "";
       if (uploadFile!=null){
           path = appConfig.getDownloadPath()+uploadFile.getPath()+"/"+ URLEncoder.encode(uploadFile.getFileName(),"UTF-8");
       }
       return path;
    }

    /**
     * 获取物理文件
     *
     * @param uploadFile
     * @return
     */
    public File getPhysicalFile(UploadFile uploadFile) {
        String path = appConfig.getBaseUploadFilePath() + uploadFile.getPath()+File.separator+uploadFile.getFileName();
        File f = new File(path);
        return f;
    }

    /**
     * 删除uploadfile,包括对应物理文件
     * @param id
     */
    public void delUploadFile(Long id) {
        UploadFile uploadFile = uploadFileRepository.findById(id).get();
        File file = new File(appConfig.getBaseUploadFilePath() + uploadFile.getPath()+File.separator+uploadFile.getFileName());
        if (FileUtils.del(file)) {
            uploadFileRepository.deleteById(id);
        }
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    public UploadFile uploadImage(MultipartFile file) {
        String path = appConfig.getImage();
        return upload(file, path);
    }

    /**
     * 上传保护油图片
     *
     * @param file
     * @return
     */
    public UploadFile uploadOlisImage(MultipartFile file) {
        String path = appConfig.getOlis();
        return upload(file, path);
    }
    /**
     * 上传临时文件
     *
     * @param file
     * @return
     */
    public UploadFile uploadTemp(MultipartFile file) {
        String path = appConfig.getTemp();
        return upload(file, path);
    }

    /**
     * 上传品牌图片
     *
     * @param file
     * @return
     */
    public UploadFile uploadBrandImage(MultipartFile file) {
        String path = appConfig.getBrand();
        return upload(file, path);
    }





    /**
     * 单文件上传
     *
     * @param file
     * @return
     */
    private UploadFile upload(MultipartFile file, String dirPath) {
        UploadFile uploadFile = null;
        if (!file.isEmpty()) {
            uploadFile = new UploadFile();
            String uniqueCode = CommonUtils.partUuidStr();
            String fileName = uniqueCode + "." + StringUtils.delete(file.getOriginalFilename()," ");
            String path = dirPath + File.separator + CommonUtils.formatTodayStr() ;
            String fullPath = appConfig.getBaseUploadFilePath() + path+File.separator + fileName;
            boolean result = FileUtils.upload(file, fullPath);
            if (result) {
                uploadFile.setFileCode(uniqueCode);
                uploadFile.setFileName(fileName);
                uploadFile.setOriginFileName(StringUtils.delete(file.getOriginalFilename()," "));
                uploadFile.setPath(path);
                uploadFile = uploadFileRepository.save(uploadFile);
            }
        }
        return uploadFile;
    }


    /**
     * 保存网络图片
     *
     * @param file
     * @return
     */
    public UploadFile uploadFromWeb(String imgUrl, String dirPath) {
        UploadFile uploadFile = null;
        if (!imgUrl.isEmpty()) {
            String uniqueCode = CommonUtils.partUuidStr();
            String fileName = uniqueCode + "." + imgUrl.substring(imgUrl.indexOf("images/")+7);
            String path = dirPath + File.separator + CommonUtils.formatTodayStr() ;
            String fullPath = appConfig.getBaseUploadFilePath() + path+File.separator + fileName;
            int i=0;
            boolean result = FileUtils.uploadFromWeb(imgUrl, fullPath,i);
            if (result) {
                uploadFile = new UploadFile();
                uploadFile.setFileCode(uniqueCode);
                uploadFile.setFileName(fileName);
                uploadFile.setOriginFileName(imgUrl.substring(imgUrl.indexOf("images/")+7));
                uploadFile.setPath(path);
                uploadFile = uploadFileRepository.save(uploadFile);
            }
        }
        return uploadFile;
    }

    /**
     * 根据upload的id获取文件实体
     *
     * @param code
     * @return
     */
    public DownloadFile getFile(String code) {
        UploadFile uploadFile = uploadFileRepository.findByFileCode(code);
        String path = appConfig.getBaseUploadFilePath() + uploadFile.getPath()+File.separator+uploadFile.getFileName();
        File file = new File(path);
        DownloadFile downloadFile = new DownloadFile();
        downloadFile.setFile(file);
        downloadFile.setUploadFile(uploadFile);
        return downloadFile;
    }


}
