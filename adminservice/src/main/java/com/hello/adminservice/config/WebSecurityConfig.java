package com.hello.adminservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.adminservice.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Hzh on 2018/6/1.
 */
/*@Configuration
@EnableWebSecurity*/
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private Logger logger = LoggerFactory.getLogger(getClass());

    LoginService loginService;
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;
    UrlAccessDecisionManager urlAccessDecisionManager;
    AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiringInspection")
    public WebSecurityConfig(LoginService loginService, UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource, UrlAccessDecisionManager urlAccessDecisionManager, AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler){
        this.loginService = loginService;
        this.urlFilterInvocationSecurityMetadataSource = urlFilterInvocationSecurityMetadataSource;
        this.urlAccessDecisionManager = urlAccessDecisionManager;
        this.authenticationAccessDeniedHandler = authenticationAccessDeniedHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      //  auth.userDetailsService(loginService);
        //账户密码解密处理
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
        //auth.authenticationProvider(new CustomAuthenticationProvider(loginService, bCryptPasswordEncoder));

        auth.userDetailsService(loginService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/","/#/**","/index.html","/upload/**","/static/**","/login_p","/common/init","/common/getRegions","/swagger-ui.html**","/druid/**","/mobile/**","/bugs/**","/olisserver/actuator/**","/olis-server/**");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	//http.authorizeRequests().antMatchers("GET","/common").permitAll().and();
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(urlAccessDecisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin()//  定义当需要用户登录时候，转到的登录页面。
                .loginPage("/login_p")// 设置登录页面
                .loginProcessingUrl("/login")// 自定义的登录接口
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
//                .defaultSuccessUrl("/#/home")
//                .failureUrl("/login_p")
                .failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

                httpServletResponse.setContentType("application/json;charset=utf-8");
                PrintWriter out = httpServletResponse.getWriter();
                StringBuffer sb = new StringBuffer();
                sb.append("{\"code\":\"60\",\"msg\":\"");
                if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
                	sb.append("用户名或密码输入错误，登录失败!");
                } else if (e instanceof DisabledException) {
                    sb.append("账户被禁用，登录失败，请联系管理员!");
                } else {
                    sb.append("登录失败!");
                }
                sb.append("\"}");
                out.write(sb.toString());
                out.flush();
                out.close();
            }
        })
                .successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
            	httpServletResponse.setContentType("application/json;charset=utf-8");
            	PrintWriter out = httpServletResponse.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
//                String s = "{\"code\":\"0\",\"msg\":" + objectMapper.writeValueAsString(loginService.currentUser()) + "}";
                String s = "{\"code\":\"0\",\"msg\":\"登录成功!\",\"user\":" + objectMapper.writeValueAsString(loginService.currentUser()) + "}";
                out.write(s);
                out.flush();
                out.close();
            }
        })
                .and()
                .sessionManagement().invalidSessionUrl("/login")//session会话过期跳转URL
                .and()
                .logout()
                .permitAll()
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(authenticationAccessDeniedHandler)
                .and();
                //.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class) //拦截每一个请求，验证token是否有效
                //.addFilterBefore(new JWTAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
}
