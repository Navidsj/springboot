package com.example.restapi.service;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {


    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receive(String message) {
    }


}
