package com.hello.gateserver;

import com.hello.common.entity.system.Permission;
import com.hello.common.entity.system.User;
import com.hello.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/27  17:35
 * @desc:
 */
@FeignClient(value = "user-server",path = "/user", fallbackFactory = UserServerHandler.class)
public interface UserServer {

    @RequestMapping(value = "/loadUserByUsername",method = RequestMethod.GET)
    Result<User> findByUsernameAndSystemType(@RequestParam("s") String s);
}
