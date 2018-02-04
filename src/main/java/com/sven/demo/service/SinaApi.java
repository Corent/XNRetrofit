package com.sven.demo.service;

import com.sven.demo.annotation.RetrofitInfo;
import com.sven.demo.module.IpInfoSina;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Sina IP地理位置API
 * @author: sven
 * @date: 02/02/2018
 */
@Component
//@RetrofitInfo(host = "http://int.dpool.sina.com.cn", intercepters = ApiAuthInterceptor.class)
public interface SinaApi {

    @GET("/iplookup/iplookup.php")
    Call<IpInfoSina> fetchIpInfo(@Query("format") String format, @Query("ip") String ip);
}
