package com.tms.stankevich.validator;

import com.tms.stankevich.domain.user.User;
import com.tms.stankevich.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegistrationFormValidator implements Validator {

	@Autowired
	UserService userService;

	@Autowired
	EmailValidator emailValidator;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		User user = (User) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty.userForm.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.userForm.email");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.userForm.password");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm","NotEmpty.userForm.confirmPassword");

		if(!emailValidator.valid(user.getEmail())){
			errors.rejectValue("email", "Pattern.userForm.email");
		}

		if (!user.getPassword().equals(user.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm", "Diff.userForm.confirmPassword");
		}

		if (userService.findUserByName(user.getUsername()).isPresent()) {
			errors.rejectValue("username", "Valid.userForm.name");
		}

		if (userService.findUserByEmail(user.getEmail()).isPresent()) {
			errors.rejectValue("email", "Valid.userForm.email");
		}
	}
}