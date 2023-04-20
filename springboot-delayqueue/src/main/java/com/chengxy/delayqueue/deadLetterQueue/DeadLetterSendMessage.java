package com.chengxy.delayqueue.deadLetterQueue;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @Author: xiaofu
 * @Description: 利用rabbitmq的存活时间和死信交换机的特性来间接实现
 * //发送带有ttl即 time to live 有时间存活的消息  消息过期会转换到死信队列  消费者监听死信队列即可完成延时消费
 */
@Component
public class DeadLetterSendMessage {

    @Resource
    private AmqpTemplate amqpTemplate;


    public void send(String delayTimes) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        amqpTemplate.convertAndSend(RabbitConstant.IOT_VIRTUAL_DEVICE_DELAY_EXCHANGE, RabbitConstant.IOT_VIRTUAL_DEVICE_DELAY_QUEUE, "大家好我是延迟数据" + delayTimes, message -> {
            message.getMessageProperties().setExpiration(delayTimes);
            System.out.println(sdf.format(new Date()) + " Delay sent.");
            return message;
        });
    }


    /**
     * @Description: messageStr发送的消息  delayTimes延时时间（ms）
     * @Author: yangy
     * @Date: 2023/4/19 15:39
     * @Params:[messageStr, delayTimes]
     * @Returns:void
     **/
    public void send(String messageStr, String delayTimes) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        amqpTemplate.convertAndSend(RabbitConstant.IOT_VIRTUAL_DEVICE_DELAY_EXCHANGE, RabbitConstant.IOT_VIRTUAL_DEVICE_DELAY_QUEUE, messageStr + " -----time to live-----" + delayTimes, message -> {
            message.getMessageProperties().setExpiration(delayTimes);//设置消息存活时间 消息过期后会转入死信队列
            System.out.println(sdf.format(new Date()) + "-------Delay message sent-------delayTimes："+delayTimes);
            return message;
        });
    }

}
