package com.hello.mobileservice.service.year;

import com.hello.common.dto.olis.Year;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  16:28
 */
@Component
public class YearServerHandler implements FallbackFactory<Year> {

    @Override
    public Year create(Throwable cause) {
        System.out.println("获取year信息失败");
        return null;
    }
}
