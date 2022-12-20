package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.example.model.Joke;
import org.example.service.JokeService;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(JokeController.class)
@AutoConfigureMockMvc(addFilters = false)
class JokeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JokeService jokeService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAll() throws Exception {
        this.mockMvc.perform(get("/jokes")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());

    }

    @Test
    void getById() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(get("/jokes/{id}", "377"))
                .andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(content).isNotNull();

    }

    @Test
    void delete() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/jokes/377").contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(content).isNotNull();

    }

    @Test
    void update() throws Exception {
        Joke joke=Joke.builder().id(1).setup("test").punchline("test").build();
        String requestBody = objectMapper.writeValueAsString(joke);
        MvcResult mvcResult = this.mockMvc.perform(put("/jokes").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody).accept(MediaType.APPLICATION_JSON))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(content).isNotNull();
    }
}