package org.example.service;


import org.example.dto.request.UpdateJokeReqDto;
import org.example.dto.response.JokeResponseDto;
import org.example.enums.PersistenceStatus;

import java.util.List;

public interface JokeService {

     List<JokeResponseDto> getAll(int page, int size) ;

     JokeResponseDto getById(long id);

     PersistenceStatus delete(long id);

     PersistenceStatus update(UpdateJokeReqDto updateJokeReqDto);
}
