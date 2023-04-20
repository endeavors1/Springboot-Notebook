package com.xiaofu.confirm.receiver;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author xiaofu
 * @description 消息消费
 * @date 2020/6/29 16:31
 */
@Slf4j
@Component
@RabbitListener(queues = "confirm_test_queue")
public class ReceiverMessage {
    int count  = 0;

/*    @RabbitHandler
    public void processHandler1(String msg, Channel channel, Message message) throws IOException {
        try {
            log.info("消费者 a 收到：{}", msg);
            //TODO 具体业务
            log.info("处理业务！！！");
            //手动异常
            //System.out.println(1 / 0);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            //如果消费异常 先加入原队列 再次消费
            //再次消费时 判断该消息是否曾被消费 如果被消费过 直接拒绝
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息重试消费失败,拒绝再次接收...");
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            } else {
                log.error("消息即将再次返回队列处理...");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }*/


/*    @RabbitHandler
    public void processHandler1(String msg, Channel channel, Message message,@Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception {
        try {
            log.info("消费者 a 收到：{}", msg);
            //TODO 具体业务
            log.info("重试次数 = {}",count);
            //手动异常
            System.out.println(1 / 0);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("消费失败！！！！");
        }
    }*/



}