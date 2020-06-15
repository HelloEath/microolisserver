package com.hello.brandserver.service.brand;

import com.hello.common.entity.system.User;
import com.hello.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/6/12  14:08
 * @desc:
 */
@FeignClient(value = "user-server",path = "/user", fallbackFactory = UserServerHandler.class)
public interface UserService {

    @RequestMapping(value = "/current-user",method = RequestMethod.GET)
    Result<User> currentUser();
}
