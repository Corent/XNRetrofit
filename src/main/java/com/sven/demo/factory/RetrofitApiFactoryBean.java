package com.sven.demo.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.sven.demo.annotation.DoNotIntercept;
import com.sven.demo.service.interceptor.AInterceptor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.Ordered;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * API工厂类
 * @author: sven
 * @date: 04/02/2018
 */
public class RetrofitApiFactoryBean extends UrlBasedRemoteAccessor implements FactoryBean, BeanClassLoaderAware, Ordered {

    private Long readTimeOut;

    private Long writeTimeOut;

    private AInterceptor[] interceptors;

    private Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong())).create();

    public RetrofitApiFactoryBean() {
    }

    @Override
    public Object getObject() throws Exception {
        String host = getServiceUrl();
        Class<?> apInterface = getServiceInterface();
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(host.endsWith("/")? host: host + "/");
        boolean flagReadTimeOut = readTimeOut != null, flagWriteTimeOut = writeTimeOut != null;
        boolean flagInterceptor = interceptors != null && interceptors.length > 0;
        if (flagReadTimeOut || flagWriteTimeOut || flagInterceptor) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            if (flagInterceptor) {
                Arrays.stream(interceptors).forEach(interceptor -> {
                    if (interceptor == null) return;
                    Set<String> apis = Arrays.stream(apInterface.getMethods()).map(method -> {
                        if (method.isAnnotationPresent(DoNotIntercept.class)) return null;
                        if (method.isAnnotationPresent(GET.class))
                            return method.getDeclaredAnnotation(GET.class).value();
                        if (method.isAnnotationPresent(POST.class))
                            return method.getDeclaredAnnotation(POST.class).value();
                        if (method.isAnnotationPresent(PUT.class))
                            return method.getDeclaredAnnotation(PUT.class).value();
                        if (method.isAnnotationPresent(DELETE.class))
                            return method.getDeclaredAnnotation(DELETE.class).value();
                        return null;
                    }).filter(api -> api != null).collect(Collectors.toSet());
                    if (apis.isEmpty()) return;
                    interceptor.addApis(apis);
                    clientBuilder.addInterceptor(interceptor);
                });
                builder.client(clientBuilder.build());
            }
            if (flagReadTimeOut) clientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            if (flagWriteTimeOut) clientBuilder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
        }
        return builder.addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(apInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return getServiceInterface();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setReadTimeOut(Long readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public void setWriteTimeOut(Long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
    }

    public void setInterceptor(AInterceptor interceptor) {
        if (interceptor == null) return;
        if (interceptors == null || interceptors.length == 0)
            this.interceptors = new AInterceptor[] { interceptor };
        else {
            List<AInterceptor> list = new ArrayList<AInterceptor>(interceptors.length + 1) {{
                add(interceptor);
                addAll(Arrays.asList(interceptors));
            }};
            interceptors = list.toArray(new AInterceptor[list.size()]);
        }
    }

    public void setInterceptors(AInterceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public int getOrder() {
        return 2147483647;
    }
}
