package org.example.flow;

import org.example.dto.JokeDto;
import org.example.service.JokeService;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.stereotype.Component;

@Component
public class JokeFlow {
    private final ConnectionFactory connectionFactory;
    private final JokeService jokeService;
    @Value("${joke.rabbitmq.queue}")
    private String queueName;

    public JokeFlow(ConnectionFactory connectionFactory, JokeService jokeService) {
        this.connectionFactory = connectionFactory;
        this.jokeService = jokeService;
    }

    @Bean
    public IntegrationFlow eventFlow() {
        return IntegrationFlows
                .from(Amqp.inboundAdapter(connectionFactory, queueName))
                .handle(JokeDto.class, ((payload, headers) -> jokeService.save(payload)))
                .log(LoggingHandler.Level.INFO, new LiteralExpression("saved successfully"))
                .get();
    }
}
