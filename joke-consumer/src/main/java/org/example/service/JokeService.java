package org.example.service;

import org.example.dto.JokeDto;
import org.example.model.Joke;

public interface JokeService {
    Joke save(JokeDto jokeDto);
}
