package com.xiaofu.confirm.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class QueueConfig {

    private static String errorTopicExchange = "error-topic-exchange";
    private static String errorQueue = "error-queue";
    private static String errorRoutingKey = "error-routing-key";

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Bean(name = "confirmTestQueue")
    public Queue confirmTestQueue() {
        return new Queue("confirm_test_queue", true, false, false);
    }

    @Bean(name = "confirmTestExchange")
    public FanoutExchange confirmTestExchange() {
        return new FanoutExchange("confirmTestExchange");
    }

    @Bean
    public Binding confirmTestFanoutExchangeAndQueue(
            @Qualifier("confirmTestExchange") FanoutExchange confirmTestExchange,
            @Qualifier("confirmTestQueue") Queue confirmTestQueue) {
        return BindingBuilder.bind(confirmTestQueue).to(confirmTestExchange);
    }

    //创建异常交换机
    @Bean
    public TopicExchange errorTopicExchange(){
        return new TopicExchange(errorTopicExchange, true, false);
    }

    //创建异常队列
    @Bean
    public Queue errorQueue(){
        return new Queue(errorQueue, true);
    }

    //队列与交换机进行绑定
    @Bean
    public Binding BindingErrorQueueAndExchange(Queue errorQueue, TopicExchange errorTopicExchange){
        return BindingBuilder.bind(errorQueue).to(errorTopicExchange).with(errorRoutingKey);
    }

    //设置MessageRecoverer
    @Bean
    public MessageRecoverer messageRecoverer(){
        //AmqpTemplate和RabbitTemplate都可以
        return new RepublishMessageRecoverer(rabbitTemplate, errorTopicExchange, errorRoutingKey);
    }

}


