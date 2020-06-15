package com.hello.gateserver;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Config role-based auth.
 *
 * @author shuaicj 2017/10/18
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationConfig config;
    @Autowired
    private userDetailsServiceImpl userDetailsService;
    @Autowired
    RedisTemplate redisTemplate;

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //  auth.userDetailsService(loginService);
        //账户密码解密处理
        BCryptPasswordEncoder bCryptPasswordEncoder= new BCryptPasswordEncoder();
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .logout().disable()
                .formLogin()
                .loginProcessingUrl("/login")// 自定义的登录接口
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
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
                        Instant now = Instant.now();
                        ObjectMapper objectMapper = new ObjectMapper();
                        String token = Jwts.builder()
                                .setSubject(authentication.getName())
                                .claim("authorities", authentication.getAuthorities().stream()
                                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                                .setIssuedAt(Date.from(now))
                                .setExpiration(Date.from(now.plusSeconds(config.getExpiration())))
                                .signWith(SignatureAlgorithm.HS256, config.getSecret().getBytes())
                                .compact();

                        httpServletResponse.addHeader(config.getHeader(), token);
                        redisTemplate.opsForValue().set(token,authentication.getPrincipal(),1, TimeUnit.HOURS);
                        PrintWriter out = httpServletResponse.getWriter();
                        String s = "{\"code\":\"0\",\"msg\":\"登录成功!\",\"Authorization\":" + objectMapper.writeValueAsString(token) + ",\"user\":" + objectMapper.writeValueAsString(authentication.getPrincipal()) + "}";
                        out.write(s);
                        out.flush();
                        out.close();
                    }
                })
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .anonymous()
                .and()
                    .exceptionHandling().authenticationEntryPoint(
                            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                    .addFilterBefore(new JwtTokenAuthenticationFilter(config),
                            UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/common/**").permitAll()
                    //.antMatchers("/mobile/**").permitAll()
               // .antMatchers("/user/**").permitAll()
                //.antMatchers("/brand/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .cors()
                .and()
                .csrf()
                .disable();
    }
}

