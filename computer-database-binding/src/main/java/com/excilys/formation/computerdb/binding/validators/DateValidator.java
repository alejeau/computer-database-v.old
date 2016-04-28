package com.excilys.formation.computerdb.validators;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.excilys.formation.computerdb.validators.annotations.ValidDate;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

	@Override
	public void initialize(ValidDate arg) {
	}

	@Override
	public boolean isValid(String date, ConstraintValidatorContext arg) {

		String dateRegex = "^^(19[7-9]{1}[0-9]{1}|20[0-2]{1}[0-9]{1}|203[0-7]{1})"
				+ "-"
				+ "(1[0-2]{1}|0[1-9]{1})"
				+ "-"
				+ "(0[1-9]{1}|[1-2]{1}[0-9]{1}|3[0-1]{1})$"
				+ "|^$";

		if (date == null || date.isEmpty()) {
			return true;
		}
		if (date.matches(dateRegex)) {
			try {
				LocalDate.parse(date);
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
}
