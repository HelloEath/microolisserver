package com.hello.brandserver.service.brand;

import com.hello.common.dto.olis.Brand;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/23  18:50
 */
@Component
public class LevelCarTypeServiceHandler implements FallbackFactory<LevelCarTypeService> {
    @Override
    public LevelCarTypeService create(Throwable cause) {
        return null;
    }
}
