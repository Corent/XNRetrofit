package com.sven.demo.annotation;

import java.lang.annotation.*;

/**
 * 用于不拦截的请求
 * @author: sven
 * @date: 03/02/2018
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DoNotIntercept {
}
