package com.hello.adminservice.util;

import com.hello.common.entity.system.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by hzh on 2018/7/19.
 */
public class PasswordUtils {

    /**
     * 使用spring security加密密码
     * @param password
     * @return
     */
    public static String encoder(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public static User currentUser(){
        try {
            return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            //System.out.println(e+"获取登陆用户信息异常");
            return null;
        }
    }
    public static void main(String[] args) {
        String encoder=encoder("363150");
        System.out.print(encoder);
    }
}
