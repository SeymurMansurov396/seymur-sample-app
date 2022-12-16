package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableScheduling
@EnableFeignClients
public class JokeProducerApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(JokeProducerApplication.class, args);
    }
}
