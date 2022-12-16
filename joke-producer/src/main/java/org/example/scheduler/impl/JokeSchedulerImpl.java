package org.example.scheduler.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.JokeDto;
import org.example.scheduler.JokeScheduler;
import org.example.service.JokeRemoteCallService;
import org.example.service.RabbitMQSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;


@Component
@AllArgsConstructor
@Slf4j
public class JokeSchedulerImpl implements JokeScheduler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

    private final JokeRemoteCallService jokeRemoteCallService;
    private final RabbitMQSender rabbitMQSender;


    @Override
    @Scheduled(initialDelay = 1000, fixedRate = 3000)
    public void fetchJokesAndPublishToRabbitMQ() {
        log.info("Delayed Regular task performed at "
                + LocalDateTime.now());
        JokeDto randomJoke = jokeRemoteCallService.getRandomJoke();
        log.info(" {} " + randomJoke);
        rabbitMQSender.send(randomJoke);
    }
}
