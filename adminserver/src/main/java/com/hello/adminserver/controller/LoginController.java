package com.hello.adminserver.controller;

import com.hello.adminserver.config.AppConfig;
import  com.hello.common.controller.common.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hzh on 2018/7/22.
 */
@RestController
public class LoginController extends BaseController{
    @Resource
    AppConfig appConfig;


    @RequestMapping("/login_p")
    public String login(HttpServletResponse resp) throws IOException {
//        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);


//        resp.setContentType("application/json;charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//        out.write("{\"status\":\"error\",\"msg\":\"尚未登录，请登录!\"}");
//        out.flush();
//        out.close();

        return "{\"status\":\"61\",\"msg\":\"尚未登录,请登录!\"}";
    }

    @RequestMapping("/login_status")
    public String loginStatus(HttpServletResponse resp) throws IOException {
        return "{\"status\":\"0\",\"msg\":\"状态：已登录\"}";
    }

    @RequestMapping(path = "/single-login-page",method = {RequestMethod.GET, RequestMethod.POST})
    public void singleLogin(HttpServletResponse resp) throws IOException{
        resp.sendRedirect(appConfig.getEctipLoginUrl());
    }


    @RequestMapping(path = "/",method = {RequestMethod.GET, RequestMethod.POST})
    public void index(HttpServletResponse resp) throws IOException{
        //resp.sendRedirect(this.serverContextPath + "/index.html");

//        return "forward:/index.html";
    }
}
