package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableMongoAuditing
public class JokeConsumerApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(JokeConsumerApplication.class, args);
    }
}
