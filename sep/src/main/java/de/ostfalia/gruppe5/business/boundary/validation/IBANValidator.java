package de.ostfalia.gruppe5.business.boundary.validation;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Named
@ApplicationScoped
public class IBANValidator implements ConstraintValidator<IBAN, String> {


    @Override
    public void initialize(IBAN iban){

    }

    @Override
    public boolean isValid(String iban, ConstraintValidatorContext constraintValidatorContext) {
        IBANValidatorHelper helper = new IBANValidatorHelper(iban);
        return helper.validateIBAN();
    }

}
