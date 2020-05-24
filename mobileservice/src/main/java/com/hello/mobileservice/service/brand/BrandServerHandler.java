package com.hello.mobileservice.service.brand;

import com.hello.common.dto.olis.Brand;
import com.hello.common.dto.olis.Year;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  18:51
 */
@Component
public class BrandServerHandler  implements FallbackFactory<Brand> {
    @Override
    public Brand create(Throwable cause) {
        System.out.print("获取品牌信息失败");
        return null;
    }
}
