package org.example.repository;

import org.example.model.Joke;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface JokeRepository extends MongoRepository<Joke, Long> , PagingAndSortingRepository<Joke,Long> {

}
