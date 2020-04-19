package com.tms.stankevich.validator;

import com.tms.stankevich.domain.movie.Hall;
import com.tms.stankevich.domain.movie.Session;
import com.tms.stankevich.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
public class SessionFormValidator implements Validator {

    @Autowired
    MovieServiceImpl userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Session.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Session session = (Session) target;
        LocalDateTime startTime = null;

      //  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startDate", "NotEmpty.movieForm.title");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateFormatJSP", "NotEmpty.movieForm.title");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.movieForm.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "hall", "NotEmpty.movieForm.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "movie", "NotEmpty.movieForm.description");

        try {
            startTime = LocalDateTime.parse(session.getDateFormatJSP().replace("T"," "), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (Exception e) {
            errors.rejectValue("dateFormatJSP", "Diff.userform.confirmPassword");
        }

        session.setStartTime(startTime);
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