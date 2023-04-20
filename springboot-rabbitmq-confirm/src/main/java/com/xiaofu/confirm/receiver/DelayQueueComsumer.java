package com.xiaofu.confirm.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Title: DelayQueueComsumer
 * @Package com.xiaofu.confirm.receiver
 * @Description: TODO
 * @Author: yangy
 * @Date: 2023/4/19 15:13
 **/


@Slf4j
@Component
@RabbitListener(queues = "ecej.iot.virtual.device.delay.queue")
public class DelayQueueComsumer {

    @RabbitHandler
    public void processHandler1(String msg) throws Exception {
        try {
            log.info("消费者 a 收到 延时队列消息：{}", msg);
            //TODO 具体业务
            log.info("开始处理业务");
        } catch (Exception e) {
            log.error("消费失败！！！！");
        }
    }


}
