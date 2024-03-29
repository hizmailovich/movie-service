package com.solvd.movie.service.impl;

import com.solvd.movie.kafka.producer.KafkaProducer;
import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.Movie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.model.fake.FkEsMovie;
import com.solvd.movie.model.fake.FkMovie;
import com.solvd.movie.service.EsMovieService;
import com.solvd.movie.service.PgMovieService;
import com.solvd.movie.web.dto.mapper.MovieMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private EsMovieService esMovieService;

    @Mock
    private PgMovieService pgMovieService;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    public void verifiesRetrieveAllByCriteria() {
        Pageable pageable = PageRequest.of(0, 20);
        EsMovie esMovie = new FkEsMovie();
        Movie movie = new FkMovie();
        SearchCriteria criteria = new SearchCriteria();
        Mockito.when(this.esMovieService.retrieveAllByCriteria(
                criteria, pageable))
                .thenReturn(Flux.just(esMovie));
        Mockito.when(this.pgMovieService.retrieveById(esMovie.getId()))
                .thenReturn(Mono.just(movie));
        Flux<Movie> movies = this.movieService.retrieveAllByCriteria(criteria, pageable);
        StepVerifier.create(movies)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesRetrieveById() {
        Movie movie = new FkMovie();
        Mockito.when(this.pgMovieService.retrieveById(movie.getId()))
                .thenReturn(Mono.just(movie));
        Mono<Movie> foundMovie = this.movieService.retrieveById(movie.getId());
        StepVerifier.create(foundMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesIsExist() {
        Long movieId = 1L;
        Mockito.when(this.pgMovieService.isExist(movieId))
                .thenReturn(Mono.just(true));
        Mono<Boolean> result = this.movieService.isExist(movieId);
        StepVerifier.create(result)
                .expectNext(true)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesCreate() {
        Movie movie = new FkMovie();
        Mockito.when(this.pgMovieService.create(movie))
                .thenReturn(Mono.just(movie));
        Mono<Movie> createdMovie = this.movieService.create(movie);
        StepVerifier.create(createdMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesUpdate() {
        Movie movie = new FkMovie();
        Mockito.when(this.pgMovieService.update(movie))
                .thenReturn(Mono.just(movie));
        Mono<Movie> updatedMovie = this.movieService.update(movie);
        StepVerifier.create(updatedMovie)
                .expectNext(movie)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    public void verifiesDelete() {
        Long movieId = 1L;
        Mockito.when(this.pgMovieService.delete(movieId))
                .thenReturn(Mono.empty());
        Mono<Void> result = this.movieService.delete(movieId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

}
