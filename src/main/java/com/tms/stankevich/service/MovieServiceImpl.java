package com.tms.stankevich.service;

import com.tms.stankevich.dao.GenreRepository;
import com.tms.stankevich.dao.MovieRepository;
import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.exception.GenreDeleteException;
import com.tms.stankevich.exception.MovieDeleteException;
import com.tms.stankevich.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private SessionService sessionService;

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
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
    public void saveOrUpdateGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findGenreById(Long id) {
        return genreRepository.findById(id);
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
}
