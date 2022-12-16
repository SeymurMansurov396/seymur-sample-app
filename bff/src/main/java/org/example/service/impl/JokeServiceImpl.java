package org.example.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.example.dto.request.UpdateJokeReqDto;
import org.example.dto.response.JokeResponseDto;
import org.example.enums.PersistenceStatus;
import org.example.exception.GeneralNoContentException;
import org.example.exception.JokeNotFoundException;
import org.example.model.Joke;
import org.example.repository.JokeRepository;
import org.example.service.JokeService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class JokeServiceImpl implements JokeService {
    private final JokeRepository jokeRepository;
    private final ModelMapper modelMapper;


    @Override
    public List<JokeResponseDto> getAll(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Joke> jokeList = jokeRepository.findAll(paging);

        if (jokeList != null && jokeList.isEmpty()) {
            throw new GeneralNoContentException("No jokes");
        }
        List<JokeResponseDto> jokes = jokeList
                .getContent()
                .stream()
                .map(joke -> modelMapper.map(joke, JokeResponseDto.class)).collect(Collectors.toList());
        return jokes;
    }

    @Override
    public JokeResponseDto getById(long id) {
        Optional<Joke> jokeOptional = jokeRepository.findById(id);
        if (!jokeOptional.isPresent()) {
            throw new JokeNotFoundException("Joke is not found with id: " + id);
        }
        JokeResponseDto joke = modelMapper.map(jokeOptional.get(), JokeResponseDto.class);
        return joke;
    }

    @Override
    public PersistenceStatus delete(long id) {
        Optional<Joke> jokeOptional = jokeRepository.findById(id);
        if (!jokeOptional.isPresent()) {
            throw new JokeNotFoundException("Joke is not found with id: " + id);
        }
        jokeRepository.delete(jokeOptional.get());
        return PersistenceStatus.DELETED;
    }

    @Override
    public PersistenceStatus update(UpdateJokeReqDto updateJokeReqDto) {
        Optional<Joke> jokeOptional = jokeRepository.findById(updateJokeReqDto.getId());
        if (!jokeOptional.isPresent()) {
            throw new JokeNotFoundException("Joke is not found with id: " + updateJokeReqDto.getId());
        }

        /*
        Joke joke = modelMapper.map(updateJokeReqDto, Joke.class);
        jokeRepository.save(joke);*/

        Joke jokeToUpdate = jokeOptional.get();
        String setup = updateJokeReqDto.getSetup();
        if (setup != null && !setup.isEmpty()) {
            jokeToUpdate.setSetup(setup);
        }
        String punchline = updateJokeReqDto.getPunchline();
        if (punchline != null && !punchline.isEmpty()) {
            jokeToUpdate.setPunchline(punchline);
        }
        jokeRepository.save(jokeToUpdate);
        return PersistenceStatus.UPDATED;
    }


}
