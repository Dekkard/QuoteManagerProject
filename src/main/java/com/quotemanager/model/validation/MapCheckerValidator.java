package com.quotemanager.model.validation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MapCheckerValidator implements ConstraintValidator<MapCheck, Map<String, String>> {
	private final static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Override
	@SuppressWarnings("unused")
	public boolean isValid(Map<String, String> value, ConstraintValidatorContext context) {
//		value.entrySet().stream().map(e -> Map.entry(LocalDate.parse(e.getKey(),dtf), new BigDecimal(e.getValue()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		context.disableDefaultConstraintViolation();
		return value.entrySet().stream().anyMatch(e -> {
			try {
				LocalDate date = LocalDate.parse(e.getKey(), dtf);
				BigDecimal price = new BigDecimal(e.getValue());
				if(price.compareTo(BigDecimal.ZERO) < 0) {
					context.buildConstraintViolationWithTemplate("Price cannot be less or equal than zero.").addConstraintViolation();
					return false;
				}
			} catch (NumberFormatException ex1) {
				context.buildConstraintViolationWithTemplate("Invalid Number.").addConstraintViolation();
				return false;
			} catch (DateTimeParseException ex2) {
				context.buildConstraintViolationWithTemplate("Invalid Date format, must follow the yyyy-mm-dd format.").addConstraintViolation();
				return false;
			}
			return true;
		});
	}
	
}
