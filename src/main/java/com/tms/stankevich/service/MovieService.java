package com.tms.stankevich.service;

import com.tms.stankevich.domain.movie.Movie;

import java.util.List;

interface MovieService {
    List<Movie> getAllMovies();
}
