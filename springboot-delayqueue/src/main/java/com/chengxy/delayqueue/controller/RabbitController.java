package com.chengxy.delayqueue.controller;//package com.xinzf.project.controller;

import com.alibaba.fastjson.JSON;
import com.chengxy.delayqueue.deadLetterQueue.DeadLetterSendMessage;
import com.chengxy.delayqueue.delayQueue.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping
public class RabbitController {

    @Autowired
    private DeadLetterSendMessage deadLetterSendMessage;

    @RequestMapping(value = "/rabbit", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> dirtRabbit(String delayTimes) throws IOException {

        deadLetterSendMessage.send(delayTimes);

        return null;
    }


    @RequestMapping(value = "/delayOrder", method = RequestMethod.GET)
    @ResponseBody
    public String sendDelay() throws IOException {

        Order order1 = new Order("Order1", 10, TimeUnit.SECONDS);//生成工单 10s后自动失效

        System.out.println("该工单："+order1);

        deadLetterSendMessage.send(order1.toString(),"10000");

        return order1.toString();
    }

    @RequestMapping(value = "/delayOrderList", method = RequestMethod.GET)
    @ResponseBody
    public void sendDelayList() throws IOException {

        Order order1 = new Order("Order1", 10, TimeUnit.SECONDS);//生成工单 10s后自动失效
        Order order2 = new Order("Order2", 20, TimeUnit.SECONDS);//生成工单 20s后自动失效
        Order order3 = new Order("Order3", 30, TimeUnit.SECONDS);//生成工单 30s后自动失效

        List<Order> delayList = new ArrayList<>();
        delayList.add(order1);
        delayList.add(order2);
        delayList.add(order3);

        delayList.forEach(order->{
            deadLetterSendMessage.send(order.toString(),order.getDelayTime());
            System.out.println("该工单："+order1);
        });
    }

}
