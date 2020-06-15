package com.hello.common.util;

import com.hello.common.entity.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;


/**
 * Created by hzh on 2018/7/19.
 */
@Component
public class PasswordUtils {
   static HttpServletRequest httpServletRequest;
   static RedisTemplate redisTemplate;

    @Autowired
    public void setHttpServletRequest(HttpServletRequest httpServletRequest){
        PasswordUtils.httpServletRequest=httpServletRequest;

    }

    @Autowired
    public  void setRedisTemplate(RedisTemplate redisTemplate) {
        PasswordUtils.redisTemplate = redisTemplate;
    }

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
            String token= httpServletRequest.getHeader("Authorization");
            if (StringUtil.isEmpty(token)){
                return null;
            }
            if (!redisTemplate.hasKey(token)){
                return null;

            }
            User user= (User) redisTemplate.opsForValue().get(token);
            return user;
            //return  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
