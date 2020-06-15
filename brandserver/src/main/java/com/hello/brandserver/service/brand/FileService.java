package com.hello.brandserver.service.brand;

import com.hello.common.entity.system.UploadFile;
import com.hello.common.util.Result;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/6/12  16:00
 * @desc:
 */
@FeignClient(value = "file-server",path = "/file", fallbackFactory = FileServiceHandler.class,configuration = FileService.MultipartSupportConfig.class)
public interface FileService {


    @RequestMapping(value = "/uploadFile",method = RequestMethod.GET,consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    Result<UploadFile> upload(@RequestPart(value = "file") MultipartFile file);
    /**
     * 引用配置类MultipartSupportConfig.并且实例化
     */
     class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        public Encoder feignFormEncoder () {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }
    }




}
