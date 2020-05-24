package com.hello.mobileservice.service.threeLevelWithYear;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  20:52
 */
@Component
public class ThreeLevelWithYearServiceHandler  implements FallbackFactory<ThreeLevelWithYearService> {
    @Override
    public ThreeLevelWithYearService create(Throwable cause) {
        System.out.print("获取信息失败");
        return null;
    }
}
