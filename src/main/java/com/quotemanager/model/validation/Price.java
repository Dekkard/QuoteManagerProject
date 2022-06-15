package com.quotemanager.model.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PriceValidator.class})
public @interface Price {
	String message() default "Invalid Format.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
