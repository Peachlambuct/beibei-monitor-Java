package com.example.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ消息队列配置
 */
@Configuration
public class RabbitConfiguration {
    @Bean("mailQueue")
    public Queue mailQueue(){
        return QueueBuilder
                .durable("mail")
                .build();
    }

    //用于处理告警信息的消息队列
    @Bean("warnQueue")
    public Queue WarnQueue(){
        return QueueBuilder
                .durable("warn")
                .build();
    }
}
