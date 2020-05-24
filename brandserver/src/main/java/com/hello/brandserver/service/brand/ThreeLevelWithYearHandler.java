package com.hello.brandserver.service.brand;

import com.hello.common.dto.olis.Brand;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/24  9:23
 */
@Component
public class ThreeLevelWithYearHandler implements FallbackFactory<ThreeLevelWithYearRepository> {
    @Override
    public ThreeLevelWithYearRepository create(Throwable cause) {
        return null;
    }
}
