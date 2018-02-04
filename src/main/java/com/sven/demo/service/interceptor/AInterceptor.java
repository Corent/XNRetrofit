package com.sven.demo.service.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 拦截器
 * @author: sven
 * @date: 03/02/2018
 */
public abstract class AInterceptor implements Interceptor {

    protected Set<String> apiSet = new HashSet<>();

    public void addApi(String api) { apiSet.add(api); }

    public void addApis(Collection<String> apis) { apiSet.addAll(apis); }

    protected boolean shouldBeIntercepted(String api) { return apiSet.contains(api); }

    protected boolean shouldBeIntercepted(Request request) { return shouldBeIntercepted(request.url().url().getPath()); }

    protected boolean shouldBeIntercepted(Chain chain) { return shouldBeIntercepted(chain.request()); }
}
