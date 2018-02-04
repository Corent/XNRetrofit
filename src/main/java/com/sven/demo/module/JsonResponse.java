package com.sven.demo.module;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Controller返回的数据格式
 * @author: sven
 * @date: 24/01/2018
 */
public class JsonResponse<T> {

    public static final int DEFAULT_SUCCESS_CODE = 0;
    public static final int DEFAULT_ERROR_CODE = 1;
    private static final int SUCCESS_CODE = 200;
    private static final String SUCCESS_MSG = "成功";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private int code;
    private String msg;

    public JsonResponse() {
    }

    public static <T> JsonResponse<T> success(T data) {
        JsonResponse<T> response = new JsonResponse();
        response.setData(data);
        response.setCode(SUCCESS_CODE);
        response.setMsg(SUCCESS_MSG);
        return response;
    }

    public static <T> JsonResponse<T> success() {
        JsonResponse<T> response = new JsonResponse();
        response.setCode(SUCCESS_CODE);
        response.setMsg(SUCCESS_MSG);
        return response;
    }

    public static <T> JsonResponse<T> success(String msg) {
        JsonResponse<T> response = new JsonResponse();
        response.setCode(SUCCESS_CODE);
        response.setMsg(msg);
        return response;
    }

    public static <T> JsonResponse<T> error(int code, String msg) {
        JsonResponse<T> response = new JsonResponse();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

    public static <T> JsonResponse<T> error(String msg) {
        return error(1, msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
