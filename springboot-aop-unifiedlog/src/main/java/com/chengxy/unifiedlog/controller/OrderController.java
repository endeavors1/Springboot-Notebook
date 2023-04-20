package com.chengxy.unifiedlog.controller;


import com.alibaba.fastjson.JSON;
import com.chengxy.unifiedlog.config.PrintlnLog;
import com.chengxy.unifiedlog.entity.OrderVO;
import com.chengxy.unifiedlog.service.impl.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * @Description: 方法上定义申明注解 利用注解aspect 生成log
     * @Author: yangy
     * @Date: 2023/3/23 15:26
     * @Params:[orderVO, name]
     * @Returns:void
     **/

    @PrintlnLog(description = "订单详情Controller")
    @RequestMapping("/order")
    public void getOrder(OrderVO orderVO, String name) {

        log.info("订单详情入参：orderVO={},name={}", JSON.toJSONString(orderVO), name);

        //OrderDTO orderInfo = orderService.getOrderInfo(orderVO);

        log.info("订单详情结果：orderInfo={}", JSON.toJSONString(""));

        //return orderInfo;
    }


    public static void main(String[] args) throws InterruptedException {
        StopWatch sw = new StopWatch("costTimeTest");
        for (int i = 0; i < 5; i++) {
            sw.start();
            System.out.println("执行:" + i);
            Thread.sleep(5000);
            sw.stop();
        }
        System.out.println(sw.prettyPrint());
    }
}
