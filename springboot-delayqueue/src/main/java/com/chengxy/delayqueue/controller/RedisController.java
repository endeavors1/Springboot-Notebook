package com.chengxy.delayqueue.controller;//package com.xinzf.project.controller;

import com.chengxy.delayqueue.delayQueue.Order;
import com.chengxy.delayqueue.redis.RedissonDelayQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping
public class RedisController {


    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private RedissonDelayQueue redissonDelayQueue;



    /**
     * @Description: 通过监听redis key过期来实现延迟队列   set key时 设置过期时间
     *      *               存在问题：跟redis key清理息息相关 可能存在延迟  即如果设置了惰性删除（主动访问key的时候）和定时清理（定时轮询随机失效的key） 而监听是key删除的时候才触发的 所以存在延迟
     * @Author: yangy
     * @Date: 2023/4/19 18:09
     * @Params:[type]
     * @Returns:java.util.Map<java.lang.String,java.lang.Object>
     **/
    @RequestMapping(value = "/redisDelay", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> redisTest(String type) throws IOException {

        Order order1 = new Order("Order1", 10, TimeUnit.SECONDS);//生成工单 10s后自动失效

        System.out.println("该工单："+order1);


        redisTemplate.opsForValue().set(order1.toString(),order1.toString(),10,TimeUnit.SECONDS);
        return null;
    }


    @RequestMapping(value = "/redissonDelay",method = RequestMethod.GET)
    @ResponseBody
    public void addTask() {
        Order order = new Order("Order", 10, TimeUnit.SECONDS);//生成工单 10s后自动失效
        redissonDelayQueue.offerTask(order.toString(), 10);
    }



}
