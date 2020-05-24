package com.hello.mobileservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hello.common.dto.olis.Device;
import com.hello.common.util.StringUtil;
import com.hello.mobileservice.service.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  14:21
 */
public class MobileInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    DeviceService deviceService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse response, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        String systemType = httpServletRequest.getHeader("systemType");
        String deviceId = httpServletRequest.getHeader("deviceId");
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        response.setContentType("application/json;charset=utf-8");

        //解决跨域（springboot 1.x版本需要设置，2.x版本不需要）
        /*response.setContentType("application/json;charset=utf-8");
        response.addHeader("Access-Control-Allow-Origin",httpServletRequest.getHeader("origin"));
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        response.addHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials","true");*/
        StringBuffer sb = new StringBuffer();
  /*      HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(MobileLoginToken.class)) {
            MobileLoginToken userLoginToken = method.getAnnotation(MobileLoginToken.class);
            if (userLoginToken.required()) {


                return true;
            }
        }*/

        // 执行认证
        if ("null".equals(token) || StringUtil.isEmpty(token)) {
            PrintWriter out = response.getWriter();
            sb.append("{\"code\":\"71\",\"status\":\"71\",\"msg\":\"");
            sb.append("未授权访问!请登录");
            sb.append("\"}");
            out.write(sb.toString());
            out.flush();
            out.close();
            return false;
        }
        if (!redisTemplate.hasKey(token)) {
            PrintWriter out = response.getWriter();
            sb.append("{\"code\":\"71\",\"status\":\"71\",\"msg\":\"");
            sb.append("token过期,请重新登录!");
            sb.append("\"}");
            out.write(sb.toString());
            out.flush();
            out.close();
            return false;
        }

        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401");
        }
        //User user = userService.findUserById(userId);
                /*if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }*/
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(deviceId+systemType)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            PrintWriter out = response.getWriter();
            sb.append("{\"code\":\"71\",\"status\":\"71\",\"msg\":\"");
            sb.append("无效token,请重新登录!");
            sb.append("\"}");
            out.write(sb.toString());
            out.flush();
            out.close();
            return false;
        }
        Device device = (Device) redisTemplate.opsForValue().get(token);

        if (device.getDevicePermission() == 1) {
            PrintWriter out = response.getWriter();
            sb.append("{\"code\":\"71\",\"status\":\"71\",\"msg\":\"");
            sb.append("设备未授权");
            sb.append("\"}");
            out.write(sb.toString());
            out.flush();
            out.close();
            return false;
        }

        //对查看油品页做限制（默认每天30次）
        if (httpServletRequest.getRequestURI().equals("/olisserver/mobile/saeList")){
            if (device.getDeviceSearchCount() == 0) {
                PrintWriter out = response.getWriter();
                sb.append("{\"code\":\"72\",\"status\":\"72\",\"msg\":\"");
                sb.append("该页面当天查询次数已超过限定次数");
                sb.append("\"}");
                out.write(sb.toString());
                out.flush();
                out.close();
                return false;
            }
            device.setDeviceSearchCount(device.getDeviceSearchCount() - 1);
            device = deviceService.save(device).getData();
            redisTemplate.opsForValue().set(token, device, 1, TimeUnit.DAYS);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
