package com.sven.demo.controller;

import com.sven.demo.module.JsonResponse;
import com.sven.demo.service.SinaService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Copyright (C) 1998 - 2018 SOHU Inc., All Rights Reserved.
 * <p>
 *
 * @author: sven
 * @date: 02/02/2018
 */
@RestController
@RequestMapping("/ipInfo")
public class IpController {

    @Resource
    private SinaService sinaService;

    @RequestMapping(value = "/{ip:\\d+\\.\\d+\\.\\d+\\.\\d+}", method = RequestMethod.GET)
    public JsonResponse<?> ipInfo(@PathVariable(value = "ip") String ip) {
        return JsonResponse.success(sinaService.fetchIpInfo(ip));
    }
}
