package com.hello.adminservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;

/**
 * 该类的主要功能就是通过当前的请求地址，获取该地址需要的用户角色
 * Created by hzh on 2018/7/19.
 */
    @Component
    public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
        //@Autowired
        //MenuService menuService;
    	private Logger logger = LoggerFactory.getLogger(getClass());
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        @Override
        public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
            //先不开启拦截,默认全都能访问
            // todo
            if(true){
//                return  null;
            }

            //获取请求地址
            String requestUrl = ((FilterInvocation) o).getRequestUrl();
            //logger.info(requestUrl, null, null, "UrlFilterInvocationSecurityMetadataSource");
            if (requestUrl.contains("swagger")) {
                return null;
            }
            if (requestUrl.contains("/v2")) {
                return null;
            }
            if ("/login_p".equals(requestUrl)) {
                return null;
            }
            if (requestUrl.contains("/ssz-single-login")) {
                return null;
            }
            if (requestUrl.contains("/single-login-page")) {
                return null;
            }
            if (requestUrl.contains("/ssz-szhyx-login")) {
                return null;
            }
            if (requestUrl.contains("/ssz-p2-login")) {
                return null;
            }

//            List<Menu> allMenu = menuService.getAllMenu();
//            for (Menu menu : allMenu) {
//                if (antPathMatcher.match(menu.getUrl(), requestUrl)&&menu.getRoles().size()>0) {
//                    List<Role> roles = menu.getRoles();
//                    int size = roles.size();
//                    String[] values = new String[size];
//                    for (int i = 0; i < size; i++) {
//                        values[i] = roles.get(i).getName();
//                    }
//                    return SecurityConfig.createList(values);
//                }
//            }
            //没有匹配上的资源，都是登录访问
            return SecurityConfig.createList("ROLE_LOGIN");
        }

        @Override
        public Collection<ConfigAttribute> getAllConfigAttributes() {
            return null;
        }

        @Override
        public boolean supports(Class<?> aClass) {
            return FilterInvocation.class.isAssignableFrom(aClass);
        }
    }
