package org.example.service;

import org.example.dto.JokeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "JokeRemoteCallService", url = "${jokes.endpoint.url}")
public interface JokeRemoteCallService {
    @GetMapping
    JokeDto getRandomJoke();

}
