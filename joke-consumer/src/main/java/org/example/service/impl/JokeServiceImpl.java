package org.example.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.JokeDto;
import org.example.model.Joke;
import org.example.repository.JokeRepository;
import org.example.service.JokeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class JokeServiceImpl implements JokeService {
    private final JokeRepository jokeRepository;
    private final ModelMapper modelMapper;


    @Override
    public Joke save(JokeDto jokeDto) {
        Joke joke = modelMapper.map(jokeDto, Joke.class);
        joke.setCreateDate(LocalDateTime.now());
        return jokeRepository.save(joke);
    }
}
