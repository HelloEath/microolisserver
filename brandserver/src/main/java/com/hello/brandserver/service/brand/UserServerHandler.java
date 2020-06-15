package com.hello.brandserver.service.brand;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/6/12  14:11
 * @desc:
 */
@Component
public class UserServerHandler implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable cause) {
        return null;
    }
}
