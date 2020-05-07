package com.tms.stankevich.validator;

import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.service.SessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;


@Component
public class HallFormValidator implements Validator {

    @Autowired
    SessionServiceImpl sessionService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Hall.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Hall hall = (Hall) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.hallForm.name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "capacity", "NotEmpty.hallForm.capacity");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.hallForm.description");

        if (hall.getCapacity() == null || hall.getCapacity() <= 0) {
            errors.rejectValue("capacity", "Valid.hallForm.capacity");
        }
        if (hall.isNew()) {
            Optional<Hall> hallByName = sessionService.findHallByName(hall.getName());
            if (hallByName.isPresent()) {
                errors.rejectValue("name", "Valid.hallForm.name");
            }
        }
    }
}