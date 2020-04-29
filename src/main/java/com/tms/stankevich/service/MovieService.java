package com.tms.stankevich.service;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.exception.GenreDeleteException;
import com.tms.stankevich.exception.MovieDeleteException;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllMovies();

    void saveOrUpdate(Movie user);

    Optional<Movie> findById(Long id);

    Optional<Movie> findByTitleYear(String title, Short year);

    void saveOrUpdateGenre(Genre genre);

    List<Genre> getAllGenres();

    Optional<Genre> findGenreById(Long id);

    Optional<Genre> findGenreByName(String name);

    void deleteGenre(Genre genre) throws GenreDeleteException;

    void deleteMovie(Movie movie) throws MovieDeleteException;
}
