package com.tms.stankevich.service;

import com.tms.stankevich.annotations.Loggable;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.exception.ConnectErrorException;
import com.tms.stankevich.exception.TimeoutException;

import java.util.List;

public interface MovieLoadService {
    List<Movie> loadMovies() throws ConnectErrorException, TimeoutException;
}
