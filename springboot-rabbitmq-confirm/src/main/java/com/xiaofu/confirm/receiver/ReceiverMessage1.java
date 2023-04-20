package com.xiaofu.confirm.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: xiaofu
 * @Description:
 */
@Slf4j
@Component
@RabbitListener(queues = "confirm_test_queue")
public class ReceiverMessage1 {

    private int retryNum = 5;

    private int currentNum = 0;




/*    @RabbitHandler
    public void processHandler(String msg, Channel channel, Message message) throws IOException {
        long devilveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println("tag:"+devilveryTag);
        try {
            log.info("消费者 2 号收到：{}", msg);
            log.info("开始消费");
            int a = 1 / 0;

            channel.basicAck(devilveryTag, false);

        } catch (Exception e) {

            log.error("尝试重试！！！！！");
            channel.basicReject(devilveryTag, true);

        }
    }*/

    @RabbitHandler
    public void processHandler(String msg, Channel channel, Message message) throws IOException {
        try {
            log.info("消费者 2 号收到：{}", msg);
            int a = 1 / 0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        }


    }

/*
    @RabbitHandler
    public void processHandler(CorrelationData correlationData, String msg, Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        int i= 0;
        message.getMessageProperties().getHeaders().put("failedCount", 0);
        System.out.println(message.getMessageProperties().getHeaders());
        try {
            log.info("消费者 b 收到：{}",msg);
            log.info("尝试消费第 {} 次",i);
            System.out.println(1 / 0);
            //System.out.println(correlationId);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(msg+"消费成功！");
        } catch (Exception e) {
            i++;
            message.getMessageProperties().getHeaders().put("failedCount", (Integer) message.getMessageProperties().getHeaders().get("failedCount") + i);
            if((Integer) message.getMessageProperties().getHeaders().get("failedCount") <= 3){
                log.error(msg+"消费失败！"+"拒绝该消息！！！");
                //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                channel.basicReject(deliveryTag, true); // 再次入列
            }else{
                log.error("消息消费失败了 {} 次,拒绝再次接收...",i);
                channel.basicReject(deliveryTag, false); // 拒绝消息
            }

        }
    }*/

/*    @RabbitHandler
    public void processTryComsumerThreeTimesHandler(CorrelationData correlationData, String msg, Channel channel, Message message) throws IOException {

        try {
            log.info("消费者 b 收到：{}",msg);
            System.out.println(1/0);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info(msg+"消费成功！");

        } catch (Exception e) {
            log.error(msg+"消费失败！"+"拒绝该消息！！！");
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }*/
}