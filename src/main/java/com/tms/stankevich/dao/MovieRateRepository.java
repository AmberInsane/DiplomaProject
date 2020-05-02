package com.tms.stankevich.dao;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.MovieRate;
import com.tms.stankevich.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRateRepository extends JpaRepository<MovieRate, Long> {
    List<MovieRate> findByUser(User user);
    List<MovieRate> findByMovie(Movie movie);
    Optional<MovieRate> findByUserAndMovie(User user, Movie movie);
}