package com.tms.stankevich.service;

import com.tms.stankevich.dao.MovieRepository;
import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }


}
