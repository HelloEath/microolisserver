package com.hello.brandserver.service.brand;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/24  9:01
 */
@FeignClient(value = "olis-server",path = "/levelCarType", fallbackFactory = FileServiceHandler.class)
public interface FileService {

    @RequestMapping(path = "/findThreeAll", method = RequestMethod.GET)
    Object uploadBrandImage(@RequestBody MultipartFile file);
}
