package com.hello.apigatewayservice.service;

import com.hello.apigatewayservice.repository.UserRepository;
import com.hello.common.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 *
 * Created by hzh on 2018/7/19.
 */
@Service
@Transactional
public class LoginService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        String []ss=s.split("\\|");
        User u = userRepository.findByUsernameAndSystemType(ss[0], ss[1]);
        //User u = userRepository.findByUsername(s);
        if (u == null) {
            throw new UsernameNotFoundException("用户名不正确");
        }
        return null;
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public User currentUser(){
        return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
