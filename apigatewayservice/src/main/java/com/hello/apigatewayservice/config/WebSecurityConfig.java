package com.hello.apigatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/5/10  13:46
 */
@EnableWebSecurity
public class WebSecurityConfig  implements WebMvcConfigurer {
    @Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager memoryUserDetailsManager=new InMemoryUserDetailsManager();
        memoryUserDetailsManager.createUser(User.withDefaultPasswordEncoder().username("admin").password("admin").roles("user").build());
        return memoryUserDetailsManager;
    }

}
