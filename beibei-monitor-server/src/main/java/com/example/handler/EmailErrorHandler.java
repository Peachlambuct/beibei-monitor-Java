package com.example.handler;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class EmailErrorHandler implements RabbitListenerErrorHandler {
    @Override
    public Object handleError(org.springframework.amqp.core.Message message, Message<?> message1, ListenerExecutionFailedException e) throws Exception {
        System.err.println("Error occurred while processing message: " + e.getMessage());
        throw new AmqpRejectAndDontRequeueException(e);
    }
}
