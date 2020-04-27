package com.tms.stankevich.validator;

import com.tms.stankevich.domain.movie.Movie;
import com.tms.stankevich.domain.movie.Ticket;
import com.tms.stankevich.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class TicketFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Ticket.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Ticket ticket = (Ticket) target;
        System.out.println(ticket);
        /*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "NotEmpty.movieForm.title");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty.movieForm.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "year", "NotEmpty.movieForm.year");

        if(movie.getYear() == null || movie.getYear() <= 1895){
            errors.rejectValue("year", "NotEmpty.movieForm.year");
        }

        if (movie.getTimeLength() == null || movie.getTimeLength() <= 0) {
            errors.rejectValue("timeLength", "NotEmpty.movieForm.timeLength");
        }*/

/*
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty.userForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "NotEmpty.userForm.address");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword","NotEmpty.userForm.confirmPassword");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sex", "NotEmpty.userForm.sex");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty.userForm.country");

		if(!emailValidator.valid(user.getEmail())){
			errors.rejectValue("email", "Pattern.userForm.email");
		}

		if(user.getNumber()==null || user.getNumber()<=0){
			errors.rejectValue("number", "NotEmpty.userForm.number");
		}

		if(user.getCountry().equalsIgnoreCase("none")){
			errors.rejectValue("country", "NotEmpty.userForm.country");
		}

		if (!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", "Diff.userform.confirmPassword");
		}

		if (user.getFramework() == null || user.getFramework().size() < 2) {
			errors.rejectValue("framework", "Valid.userForm.framework");
		}

		if (user.getSkill() == null || user.getSkill().size() < 3) {
			errors.rejectValue("skill", "Valid.userForm.skill");
		}*/

    }

}