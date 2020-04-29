package com.tms.stankevich.validator;

import com.tms.stankevich.domain.movie.Genre;
import com.tms.stankevich.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class GenreFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Genre.class.equals(clazz);
    }

    @Autowired
    MovieServiceImpl movieService;

    @Override
    public void validate(Object target, Errors errors) {

        Genre genre = (Genre) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.genreForm.name");

        if (genre.isNew()) {
            Optional<Genre> genreByName = movieService.findGenreByName(genre.getName());
            if (genreByName.isPresent()) {
                errors.rejectValue("name", "Valid.genreForm.name");
            }
        }
    }

}