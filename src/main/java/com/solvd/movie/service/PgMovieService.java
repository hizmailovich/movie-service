package com.solvd.movie.service;

import com.solvd.movie.model.Movie;
import reactor.core.publisher.Mono;

/**
 * MovieService for Postgresql
 */

public interface PgMovieService {

    Mono<Movie> retrieveById(Long movieId);

    Mono<Boolean> isExist(Long movieId);

    Mono<Movie> create(Movie movie);

    Mono<Movie> update(Movie movie);

    Mono<Void> delete(Long movieId);

}