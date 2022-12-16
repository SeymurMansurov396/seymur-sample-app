package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.JokeDto;
import org.example.model.Joke;
import org.example.service.JokeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
@Slf4j
public class TestController {

    private final JokeService jokeService;

    @GetMapping("/{id}")
    public ResponseEntity<?> test(@PathVariable long id) {
        log.info("Id: "+id);
        jokeService.save(new JokeDto(id, "type", "setup", "punchline"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
