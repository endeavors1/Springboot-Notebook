package com.xiaofu.limit.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xiaofu
 * @Description:
 */
@Controller
public class RedisController {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @GetMapping("/addKey")
    @ResponseBody
    public String addkey() {

        for (int i = 0; i < 10; i++) {

            //添加String类型
            redisTemplate.opsForValue().set("test_key_" + i, i);
            //redisTemplate.opsForList().rightPop("test_key_" + i);
            //添加List类型
            redisTemplate.opsForList().rightPush("test_list",i);



        }

        //过期
        redisTemplate.opsForValue().set("test_expire","5s过期",5000, TimeUnit.MILLISECONDS);
        Map<String,String> strMap = new HashMap<>();
        strMap.put("yangy","杨洋");
        //添加map
        redisTemplate.opsForHash().putAll("testMap",strMap);
        redisTemplate.opsForHash().put("testMap","zhangsan","张三");


        System.out.println(redisTemplate.opsForHash().entries("testMap"));
        System.out.println(redisTemplate.opsForHash().get("testMap","yangy"));

        return "OK";
    }

    @GetMapping("/getLock")
    @ResponseBody
    public void lockService() throws InterruptedException {
        trylock();
    }


    public void trylock() throws InterruptedException {
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("myLock","aaaaa");
        if(lock){
            System.out.println("获取到锁 执行逻辑代码");
            Object value = redisTemplate.opsForValue().get("num");
            if (value == null) {
                return;
            }
            int num = Integer.parseInt(String.valueOf(value));
            redisTemplate.opsForValue().set("num",++num);
            Thread.sleep(2000);
            redisTemplate.delete("myLock");
            System.out.println(redisTemplate.opsForValue().get("num"));
        }else{
            System.out.println("没有取到锁 继续自旋");
            Thread.sleep(1000);
            trylock();
        }
    }


    public void trylock2() throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisTemplate.opsForValue().setIfAbsent("myLock",uuid);
        if(lock){
            System.out.println("获取到锁 执行逻辑代码");
            Object value = redisTemplate.opsForValue().get("num");
            if (value == null) {
                return;
            }
            int num = Integer.parseInt(String.valueOf(value));
            redisTemplate.opsForValue().set("num",++num);
            Thread.sleep(2000);
            if (uuid.equals((String) redisTemplate.opsForValue().get("locks"))){
                redisTemplate.delete("myLock");
            }
            System.out.println(redisTemplate.opsForValue().get("num"));
        }else{
            System.out.println("没有取到锁 继续自旋");
            Thread.sleep(1000);
            trylock();
        }
    }

}