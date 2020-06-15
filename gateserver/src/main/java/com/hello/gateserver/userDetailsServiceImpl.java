package com.hello.gateserver;

import com.hello.common.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/27  17:28
 * @desc:
 */
@Service
public class userDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserServer userServer;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String []ss=username.split("\\|");
        User u = userServer.findByUsernameAndSystemType(username).getData();

        if (u == null) {
            throw new UsernameNotFoundException("用户名不正确");
        }
        return u;
    }
}
