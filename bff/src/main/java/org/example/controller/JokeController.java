package org.example.controller;

import org.example.dto.request.UpdateJokeReqDto;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.JokeResponseDto;
import org.example.enums.PersistenceStatus;
import org.example.service.JokeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jokes")
public class JokeController {

    private final JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll( @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        List<JokeResponseDto> jokes = jokeService.getAll(page,size);
        return new ResponseEntity<>(new ApiResponse(true, jokes), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable long id) {
        JokeResponseDto joke = jokeService.getById(id);
        return new ResponseEntity<>(new ApiResponse(true, joke), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable long id) {
        return new ResponseEntity<>(new ApiResponse(jokeService.delete(id).toString(),true), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateJokeReqDto updateJokeReqDto){
          PersistenceStatus persistenceStatus= jokeService.update(updateJokeReqDto);
          return new ResponseEntity<>(new ApiResponse(persistenceStatus.toString(),true),HttpStatus.OK);
    }

}
