package com.tms.stankevich.service;

import com.tms.stankevich.domain.movie.Movie;

import java.util.List;

public interface MovieLoadService {
    List<Movie> load();
}
