package com.yangy.nacos.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: RestController
 * @Package com.yangy.nacos.controller
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/3/30 11:02
 **/


@RestController
@RefreshScope
@RequestMapping
public class TestController {


    @Value("${spring.application.name}")
    private String config1;


    @Value("${myname}")
    private String myname;


    @Value("${testname}")
    private String testname;


    @GetMapping("/configs")
    public String getConfig(){
        System.out.println("配置config:"+config1);
        System.out.println("配置myname:"+myname);
        System.out.println("配置testname:"+testname);
        return config1;
    }

}
