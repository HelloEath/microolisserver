package com.hello.brandserver.service.brand;

import com.hello.common.dto.olis.Brand;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/24  9:22
 */
@Component
public class FileServiceHandler implements FallbackFactory<FileService> {
    @Override
    public FileService create(Throwable cause) {
        return null;
    }
}
