package com.sven.demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.sven.demo.annotation.DoNotIntercept;
import com.sven.demo.annotation.RetrofitInfo;
import com.sven.demo.service.interceptor.AInterceptor;
import com.sven.demo.service.SinaApi;
import okhttp3.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.web.context.ContextLoader;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 通过注解形式注入
 * @author: sven
 * @date: 04/02/2018
 */
@Configuration
public class ApiConfig {

    /*@Bean(name = "sinaApi")
    public SinaApi getSinaApi() {
        return apiFactory(SinaApi.class);
    }

    private ApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");

    private Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong())).create();

    private <T> T apiFactory(Class<T> clazz) {
        RetrofitInfo retrofitInfo = clazz.getAnnotation(RetrofitInfo.class);
        if (retrofitInfo == null) return null;
        String host = retrofitInfo.host();
        Class<? extends AInterceptor>[] interceptors = retrofitInfo.intercepters();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(host.endsWith("/")? host: host + "/");
        if (interceptors != null && interceptors.length > 0) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            Arrays.stream(interceptors).forEach(iClazz -> {
                AInterceptor interceptor = applicationContext.getBean(iClazz);
                if (interceptor == null) return;
                Set<String> apis = Arrays.stream(clazz.getMethods()).map(method -> {
                    if (method.isAnnotationPresent(DoNotIntercept.class)) return null;
                    if (method.isAnnotationPresent(GET.class)) return method.getDeclaredAnnotation(GET.class).value();
                    if (method.isAnnotationPresent(POST.class)) return method.getDeclaredAnnotation(POST.class).value();
                    if (method.isAnnotationPresent(PUT.class)) return method.getDeclaredAnnotation(PUT.class).value();
                    if (method.isAnnotationPresent(DELETE.class)) return method.getDeclaredAnnotation(DELETE.class).value();
                    return null;
                }).filter(api -> api != null).collect(Collectors.toSet());
                if (apis.isEmpty()) return;
                interceptor.addApis(apis);
                clientBuilder.addInterceptor(interceptor);
            });
            builder.client(clientBuilder.build());
        }
        return builder.addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(clazz);
    }*/
}
