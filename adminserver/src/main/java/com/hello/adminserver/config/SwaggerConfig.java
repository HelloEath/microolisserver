package com.hello.adminserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by hzh on 2018/6/21.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()  // 选择那些路径和api会生成document
                //.apis(RequestHandlerSelectors.any()) // 对所有api进行监控
                .apis(RequestHandlerSelectors.basePackage("com.hello.adminserver.controller")) // 指定扫描的包
                .paths(PathSelectors.any()) // 对所有路径进行监控;
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API接口文档")
                .description("API接口文档，文档内容会根据服务端代码自动更新。")
                .contact("hzh,elliot_hzh@qq.com,2018")
                .version("1.0.0")
                .build();
    }
}
