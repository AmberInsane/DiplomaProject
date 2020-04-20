package com.tms.stankevich.dao;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findById(Long id);
    Optional<Movie> findByTitle(String title);
    List<Movie> findByYear(Short year);
    List<Movie> findByGenre(Genre genre);
}