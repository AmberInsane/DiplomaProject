package com.tms.stankevich.service;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.MovieRate;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.exception.GenreDeleteException;
import com.tms.stankevich.exception.MovieDeleteException;
import com.tms.stankevich.exception.MovieRateException;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> getAllMovies();

    List<Movie> getAllMoviesOrdered();

    void saveOrUpdate(Movie user);

    Optional<Movie> findById(Long id);

    Optional<Movie> findByTitleYear(String title, Short year);

    Genre saveOrUpdateGenre(Genre genre);

    List<Genre> getAllGenres();

    Optional<Genre> findGenreById(Long id);

    Optional<Genre> findGenreByName(String name);

    void deleteGenre(Genre genre) throws GenreDeleteException;

    void deleteMovie(Movie movie) throws MovieDeleteException;

    List<Integer> getRatesList();

    void rateMovie(Movie movie, User user, Integer rate) throws MovieRateException;

    Optional<MovieRate> getUserMovieRate(User user, Movie movie);

    List<Movie> getMoviesByGenre(Genre genre);

    List<Movie> getMoviesByYear(Short year);
}
