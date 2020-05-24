package com.hello.mobileservice.service.saesc;

import com.hello.common.dto.olis.SaeDesc;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  21:02
 */
@Component
public class SaeDescServiceHandler implements FallbackFactory<SaeDesc> {
    @Override
    public SaeDesc create(Throwable cause) {
        System.out.print("获取sadesc信息失败");
        return null;
    }
}
