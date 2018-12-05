package de.ostfalia.gruppe5.business.boundary.validation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Named
@ApplicationScoped
public class IBANValidator implements ConstraintValidator<IBAN, String> {

	@Inject
	IBANValidatorHelper helper;

	/**
	 * Compliance to at least Java EE 7 Seems to be empty most of the time
	 * 
	 * @param iban
	 */
	@Override
	public void initialize(IBAN iban) {

	}

	@Override
	public boolean isValid(String iban, ConstraintValidatorContext constraintValidatorContext) {
		helper.build(iban);
		return helper.validateIBAN();
	}

}
