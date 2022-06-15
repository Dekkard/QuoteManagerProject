package com.quotemanager.model.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<Price, BigDecimal>{

	@Override
	public boolean isValid(BigDecimal price, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		if(price.compareTo(BigDecimal.ZERO) < 0) {
			context.buildConstraintViolationWithTemplate("Price cannot be less or equal than zero.").addConstraintViolation();
			return false;
		}
		return true;
	}

}
