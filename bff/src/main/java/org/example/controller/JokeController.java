package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.UpdateJokeReqDto;
import org.example.dto.response.ApiResponse;
import org.example.dto.response.JokeResponseDto;
import org.example.enums.PersistenceStatus;
import org.example.service.JokeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jokes")
@Slf4j
public class JokeController {

    private final JokeService jokeService;

    public JokeController(JokeService jokeService) {
        this.jokeService = jokeService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('JOKE_READ')")
    public ResponseEntity<ApiResponse> getAll(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        log.info("jokes request: ");
        List<JokeResponseDto> jokes = jokeService.getAll(page, size);
        ApiResponse apiResponse = new ApiResponse(true, jokes);
        log.info("jokes response: " + apiResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('JOKE_READ')")
    public ResponseEntity<ApiResponse> getById(@PathVariable long id) {
        log.info("request: " + id);
        JokeResponseDto joke = jokeService.getById(id);
        ApiResponse apiResponse = new ApiResponse(true, joke);
        log.info("response: " + apiResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('JOKE_DELETE')")
    public ResponseEntity<ApiResponse> delete(@PathVariable long id) {
        log.info("request: " + id);
        ApiResponse apiResponse = new ApiResponse(jokeService.delete(id).toString(), true);
        log.info("response: " + apiResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('JOKE_UPDATE')")
    public ResponseEntity<ApiResponse> update(@RequestBody UpdateJokeReqDto updateJokeReqDto) {
        log.info("request: " + updateJokeReqDto);
        PersistenceStatus persistenceStatus = jokeService.update(updateJokeReqDto);
        ApiResponse apiResponse = new ApiResponse(persistenceStatus.toString(), true);
        log.info("response: " + apiResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

  /*  @PreAuthorize("hasAuthority('SCOPE_roles')")
    @GetMapping("/ping")
    public String ping() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return "Scopes: " + authentication.getAuthorities();
    }

    @PreAuthorize("hasAnyRole('JOKE_READ')")
    @GetMapping("/test1")
    public String test1() {

        return "Test1: ";
    }

    @PreAuthorize("hasAnyRole('JOKE_DELETE')")
    @GetMapping("/test2")
    public String test2() {

        return "Test2: ";
    }*/

}
