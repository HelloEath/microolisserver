package com.hello.apigatewayservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * app全局自定义配置类
 * Created by hzh on 2018/7/13.
 */
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfig {

        private String baseUploadFilePath;
        private String downloadPath;
        private String image;
        private String document;
        private String template;
        private String temp;
        private String ectipLoginUrl;
        private String olis;
        private String brand;
        private String engine;



}
