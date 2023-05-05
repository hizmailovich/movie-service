package com.solvd.movie.service.impl;

import com.solvd.movie.model.EsMovie;
import com.solvd.movie.model.criteria.SearchCriteria;
import com.solvd.movie.persistence.EsRepository;
import com.solvd.movie.service.EsMovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EsMovieServiceImpl implements EsMovieService {

    private final EsRepository esRepository;

    @Override
    public Flux<EsMovie> retrieveAllByCriteria(final SearchCriteria criteria) {
        Flux<EsMovie> movies;
        if (criteria.getName() != null && criteria.getYear() != null) {
            movies = this.esRepository.findAllByNameAndYear(
                    criteria.getName(),
                    criteria.getYear()
            );
        } else if (criteria.getName() != null) {
            movies = this.esRepository.findAllByName(criteria.getName());
        } else if (criteria.getYear() != null) {
            movies = this.esRepository.findAllByYear(criteria.getYear());
        } else {
            movies = this.esRepository.findAll();
        }
        return movies;
    }

    @Override
    @Transactional
    public Mono<EsMovie> create(final EsMovie movie) {
        return this.esRepository.save(movie);
    }

    @Override
    @Transactional
    public Mono<EsMovie> update(final EsMovie movie) {
        return this.esRepository.findById(movie.getId())
                .flatMap(foundMovie -> {
                    foundMovie.setName(movie.getName());
                    foundMovie.setYear(movie.getYear());
                    foundMovie.setDescription(movie.getDescription());
                    return this.esRepository.save(movie);
                });
    }

    @Override
    @Transactional
    public Mono<Void> delete(final Long movieId) {
        return this.esRepository.deleteById(movieId);
    }

}
