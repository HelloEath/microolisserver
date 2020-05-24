package com.hello.adminserver2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Adminserver2Application {

    public static void main(String[] args) {
        SpringApplication.run(Adminserver2Application.class, args);
    }

}
