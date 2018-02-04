package com.sven.demo.advice;

import com.sven.demo.module.JsonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 拦截所有Collector的Exception，保证返回HTTP 200
 * @author: sven
 * @date: 04/02/2018
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    public JsonResponse<?> exceptionHandler(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return JsonResponse.error(JsonResponse.DEFAULT_ERROR_CODE, "系统异常");
    }
}