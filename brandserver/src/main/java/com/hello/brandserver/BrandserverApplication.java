package com.hello.brandserver;

import com.hello.common.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
@EnableDiscoveryClient
@EntityScan(basePackages ={"com.hello.common.*"})
@EnableConfigurationProperties({AppConfig.class})
@EnableFeignClients
@ComponentScan(basePackages = {"com.hello.common.*","com.hello.brandserver.*"})
public class BrandserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrandserverApplication.class, args);
    }

}
