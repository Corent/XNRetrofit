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
public class ApiAuthInterceptor extends ParamInterceptor {

    @Value("${secret_key}")
    private String SECRET_KEY;

    @Override
    public void handle(Map<String, String> params, Headers headers) {
        if (params.get("sign") != null) return;
        params.put("sign", DigestUtils.md5Hex(new TreeMap<>(params).entrySet().stream().map(e -> e.getKey() + "=" + e.getValue())
                .reduce("", String::concat) + SECRET_KEY));
    }
}
