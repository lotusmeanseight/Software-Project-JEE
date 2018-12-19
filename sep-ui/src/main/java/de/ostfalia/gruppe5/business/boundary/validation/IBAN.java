package de.ostfalia.gruppe5.business.boundary.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { IBANValidator.class })
@Documented
public @interface IBAN {
	String message() default "Invalid IBAN";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}