package com.tms.stankevich.validator;

import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class MovieFormValidator implements Validator {

    @Autowired
    MovieServiceImpl userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Movie.class.equals(clazz);
    }

    @Value("movie.min.year")
    private int minYear;

    @Override
    public void validate(Object target, Errors errors) {
        Movie movie = (Movie) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty.movieForm.title");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "genre", "NotEmpty.movieForm.genre");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.movieForm.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "year", "NotEmpty.movieForm.year");

        if (movie.getYear() == null || movie.getYear() <= minYear) {
            errors.rejectValue("year", "Valid.movieForm.year");
        }

        if (movie.getTimeLength() == null || movie.getTimeLength() <= 0) {
            errors.rejectValue("timeLength", "NotEmpty.movieForm.timeLength");
        }
    }
}