package com.sven.demo.annotation;

import com.sven.demo.service.interceptor.AInterceptor;

import java.lang.annotation.*;

/**
 * API的Retrofit元数据
 * @author: sven
 * @date: 03/02/2018
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RetrofitInfo {

    String host();

    Class<? extends AInterceptor>[] intercepters() default {};
}
