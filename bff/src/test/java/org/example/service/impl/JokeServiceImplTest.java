package org.example.service.impl;

import org.aspectj.lang.annotation.Before;
import org.example.dto.response.JokeResponseDto;
import org.example.exception.JokeNotFoundException;
import org.example.repository.JokeRepository;
import org.example.model.Joke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;




@ExtendWith(MockitoExtension.class)
class JokeServiceImplTest {
    @Mock
    private JokeRepository jokeRepository;
    @Spy
    private ModelMapper modelMapper;


    @InjectMocks
    private JokeServiceImpl jokeService;

    @Test
    void getAll() {
        Pageable paging = PageRequest.of(1, 10);
        List<Joke> list = new ArrayList<>();
        list.add(new Joke().builder().id(1).build());
        list.add(new Joke().builder().id(2).build());
        Page<Joke> jokeList = new PageImpl<>(list);
        when(jokeRepository.findAll(paging)).thenReturn(jokeList);
        assertThat(jokeService.getAll(1, 10)).hasSize(2);
        verifyNoMoreInteractions(jokeRepository);
    }

    @Test
    void getById() {

        Optional<Joke> jokeOptional = Optional.ofNullable(new Joke().builder().id(377l).type("general").build());
        when(jokeRepository.findById(anyLong())).thenReturn(jokeOptional);
        JokeResponseDto joke = jokeService.getById(anyLong());
        System.out.println(joke);
        assertThat(joke).isNotNull();
        assertThat(joke.getId()).isEqualTo(377l);
        assertThat(joke.getType()).isEqualTo("general");
        verifyNoMoreInteractions(jokeRepository);

    }

    @Test
    void delete() throws Exception {

        Joke expected = new Joke().builder().id(1l).punchline("test").setup("test").build();


    }

    @Test
    void update() {
        Optional<Joke> jokeOptional = Optional.ofNullable(new Joke().builder().id(377l).type("general").build());
        when(jokeRepository.findById(anyLong())).thenReturn(jokeOptional);

    }


}