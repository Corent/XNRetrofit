package com.sven.demo.service;

import com.sven.demo.module.IpInfoSina;
import com.sven.demo.util.RetrofitUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Sina IP地理位置服务
 * @author: sven
 * @date: 02/02/2018
 */
@Service
public class SinaService {

    @Resource
    private SinaApi sinaApi;

    @Resource
    private RetrofitUtil<IpInfoSina> ipInfoSinaRetrofit;

    public IpInfoSina fetchIpInfo(String ip) {
        return ipInfoSinaRetrofit.fetch(sinaApi.fetchIpInfo("json", ip));
    }
}
