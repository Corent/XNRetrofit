package com.sven.demo.service.interceptor;

import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 接口签名拦截器
 * @author: sven
 * @date: 02/02/2018
 */
@Component
public class ApiAuthInterceptor extends AInterceptor {

    @Value("${secret_key}")
    private String SECRET_KEY;

    public void handle(Map<String, String> params, Headers headers) {
        if (params.get("sign") != null) return;
        TreeMap<String, String> sParams = new TreeMap<>(params);
        params.put("sign", DigestUtils.md5Hex(sParams.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
                .reduce("", String::concat) + SECRET_KEY));
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!shouldBeIntercepted(request)) return chain.proceed(request);
        HttpUrl httpUrl = request.url();
        Headers headers = request.headers();
        Map<String, String> params = null;
        switch (request.method()) {
            case "GET":
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
