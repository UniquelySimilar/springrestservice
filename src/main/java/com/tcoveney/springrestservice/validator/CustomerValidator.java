package com.tcoveney.springrestservice.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.tcoveney.springrestservice.model.Customer;

public class CustomerValidator implements Validator {

	@Override
	public boolean supports(Class clazz) {
		return Customer.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "street", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "state", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "zipcode", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "homePhone", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required");
	}

}
