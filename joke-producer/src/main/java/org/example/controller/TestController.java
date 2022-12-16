package org.example.controller;

import org.example.service.RabbitMQSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private  final RabbitMQSender rabbitMQSender;

    public TestController(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    @GetMapping("/{word}")
    public String producer(@PathVariable("word") String word) {


        rabbitMQSender.send(word);

        return "Message sent to the RabbitMQ joke Successfully";
    }
}
