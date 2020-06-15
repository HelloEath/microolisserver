package com.hello.gateserver;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/27  18:01
 * @desc:
 */
@Component
public class UserServerHandler implements FallbackFactory<UserServer> {
    @Override
    public UserServer create(Throwable cause) {
        return null;
    }
}
