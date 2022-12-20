package org.example.repository;

import org.example.model.Joke;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import java.util.List;
import static org.assertj.core.api.Assertions.*;


@DataMongoTest
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
public class JokeRepositoryTest {
    @Autowired
    private JokeRepository jokeRepository;




    @Test
    public void test_save() {
        Joke j = Joke.builder().id(1).type("general").setup("Why do bees hum?").punchline("Because they don't know the words.").build();
        Joke saved = jokeRepository.save(j);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isGreaterThan(0);
    }

    @Test
    public void test_find_all() {
        Joke j1 = Joke.builder().id(1).type("general").setup("Why do bees hum?").punchline("Because they don't know the words.").build();
        Joke j2 = Joke.builder().id(2).type("programming").setup("java").punchline("fun").build();
        jokeRepository.save(j1);
        jokeRepository.save(j2);
        List<Joke> jokes = jokeRepository.findAll();

        assertThat(jokes).hasSize(2).contains(jokes.toArray(new Joke[2]));

    }

    @Test
    public void test_update() {
        Joke j = Joke.builder().id(3).type("general").setup("Why do bees hum?").punchline("Because they don't know the words.").build();
        jokeRepository.save(j);
        Joke jokeToUpdate = jokeRepository.findById(3l).get();
        jokeToUpdate.setType("programming");
        jokeRepository.save(jokeToUpdate);
        assertThat(jokeRepository.findById(3l).get().getType()).isEqualTo("programming");
    }

    @Test
    public void test_delete() {
        Joke j = Joke.builder().id(3).type("general").setup("Why do bees hum?").punchline("Because they don't know the words.").build();
        jokeRepository.save(j);

        jokeRepository.deleteById(3l);
        boolean exists = jokeRepository.existsById(3l);
        assertThat(exists).isEqualTo(false);
    }

}