package com.hello.apigatewayservice.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Hello_Earther(ShenYongJian)
 * @date 2020/4/25  10:01
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)

public @interface PassToken {
    boolean required() default true;
}
