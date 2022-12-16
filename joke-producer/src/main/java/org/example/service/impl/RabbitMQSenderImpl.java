package org.example.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.service.RabbitMQSender;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQSenderImpl implements RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;

    @Value("${joke.rabbitmq.exchange}")
    private String exchange;

    @Value("${joke.rabbitmq.routingkey}")
    private String routingkey;

    public RabbitMQSenderImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void send(Object data) {
        log.info("sending");
        rabbitTemplate.convertAndSend(exchange, routingkey, data);
        log.info("Sent msg = " + data);
    }
}
