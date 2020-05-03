package com.tms.stankevich.service;

import com.tms.stankevich.dao.GenreRepository;
import com.tms.stankevich.dao.MovieRateRepository;
import com.tms.stankevich.dao.MovieRepository;
import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.MovieRate;
import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.exception.GenreDeleteException;
import com.tms.stankevich.exception.MovieDeleteException;
import com.tms.stankevich.exception.MovieRateException;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieRateRepository movieRateRepository;

    @Autowired
    private SessionService sessionService;

    @Value("${movie.min.rate}")
    private Integer minRate;

    @Value("${movie.max.rate}")
    private Integer maxRate;

    private List<Integer> ratesList;

    @PostConstruct
    public void reset() {
        ratesList = IntStream.rangeClosed(minRate, maxRate)
                .boxed().collect(Collectors.toList());
    }


    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> getAllMoviesOrdered() {
        return movieRepository.findAllByOrderByTitle();
    }

    @Override
    public void saveOrUpdate(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Override
    public Optional<Movie> findByTitleYear(String title, Short year) {
        return movieRepository.findByTitleAndYear(title, year);
    }

    @Override
    public void saveOrUpdateGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAllByOrderByName();
    }

    @Override
    public Optional<Genre> findGenreById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Optional<Genre> findGenreByName(String name) {
        return genreRepository.findByName(name);
    }

    @Override
    public void deleteGenre(Genre genre) throws GenreDeleteException {
        List<Movie> movies = movieRepository.findByGenre(genre);
        if (movies.size() > 0)
            throw new GenreDeleteException(movies.size());
        genreRepository.delete(genre);
    }

    @Override
    public void deleteMovie(Movie movie) throws MovieDeleteException {
        List<Session> sessions = sessionService.findByMovie(movie);
        if (sessions.size() > 0)
            throw new MovieDeleteException(sessions.size());
        movieRepository.delete(movie);
    }

    @Override
    public List<Integer> getRatesList() {
        return ratesList;
    }

    @Override
    public void rateMovie(Movie movie, User user, Integer rateValue) throws MovieRateException {
        if (rateValue < minRate || rateValue > maxRate)
            throw new MovieRateException("message.movie.rate.bounds");
        Optional<MovieRate> oldRate = movieRateRepository.findByUserAndMovie(user, movie);
        MovieRate rate;
        if (oldRate.isPresent()) {
            rate = oldRate.get();
        } else {
            rate = new MovieRate();
            rate.setMovie(movie);
            rate.setUser(user);
        }
        rate.setValue(rateValue);
        movieRateRepository.save(rate);
    }

    @Override
    public Optional<MovieRate> getUserMovieRate(User user, Movie movie) {
        return movieRateRepository.findByUserAndMovie(user, movie);
    }

    @Override
    public List<Movie> getMoviesByGenre(Genre genre) {
        return movieRepository.findByGenre(genre);
    }

}
