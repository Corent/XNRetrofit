package com.sven.demo.service.interceptor;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Copyright (C) 1998 - 2018 SOHU Inc., All Rights Reserved.
 * <p>
 *
 * @author: sven
 * @date: 07/02/2018
 */
public abstract class ParamInterceptor extends AInterceptor {

    public abstract void handle(Map<String, String> params, Headers headers);

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!shouldBeIntercepted(request)) return chain.proceed(request);   //判断是否应该拦截
        Headers headers = request.headers();
        Map<String, String> params = null;
        switch (request.method()) {
            case "GET":
                HttpUrl httpUrl = request.url();
                params = httpUrl.queryParameterNames().parallelStream().collect(Collectors.toMap(name -> name, name -> httpUrl.queryParameter(name)));
                handle(params, headers);
                HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
                params.entrySet().parallelStream().forEach(e -> httpUrlBuilder.setQueryParameter(e.getKey(), e.getValue()));
                request = request.newBuilder().url(httpUrlBuilder.build()).headers(headers).build();
                break;
            case "POST":
                RequestBody body = request.body();
                if (!(body instanceof FormBody)) break;
                params = new HashMap<>();
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                FormBody formBody = (FormBody) body;
                for (int i = 0; i < formBody.size(); i++) params.put(formBody.encodedName(i), formBody.encodedValue(i));
                handle(params, headers);
                params.entrySet().parallelStream().forEach(e -> bodyBuilder.addEncoded(e.getKey(), e.getValue()));
                request = request.newBuilder().post(formBody).headers(headers).build();
                break;
            case "PUT": case "DELETE": default: break;
        }
        return chain.proceed(request);
    }
}
