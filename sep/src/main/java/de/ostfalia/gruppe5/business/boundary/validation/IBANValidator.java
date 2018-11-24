package de.ostfalia.gruppe5.business.boundary.validation;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;
import java.util.Arrays;

@Named
@ApplicationScoped
public class IBANValidator implements ConstraintValidator<IBAN, String> {

    private static int MODULO = 97;
    private static int COUNTRY_CODE_START_INDEX = 0;
    private static int COUNTRY_CODE_END_INDEX = COUNTRY_CODE_START_INDEX + 1;
    private static int CHECK_DIGIT_START_INDEX = COUNTRY_CODE_START_INDEX;
    private static int CHECK_DIGIT_END_INDEX = CHECK_DIGIT_START_INDEX + 1;
    private static int BBAN_INDEX = CHECK_DIGIT_END_INDEX + 1;
    private static int START_ALPHANUMERICAL = 'A' + 1;


    @Override
    public void initialize(IBAN iban){

    }

    @Override
    public boolean isValid(String check, ConstraintValidatorContext constraintValidatorContext) {
        return (modulo(check)).equals(BigInteger.ONE);
    }

    private BigInteger modulo(String iban){
        int[] countryCodeToInt = iban.chars().limit(COUNTRY_CODE_END_INDEX+1).map(this::transformInteger).toArray();
        StringBuilder builder = new StringBuilder();
        builder.append(iban.substring(4));
        builder.append(Arrays.stream(countryCodeToInt));
        builder.append(iban.substring(CHECK_DIGIT_START_INDEX,CHECK_DIGIT_END_INDEX));
        BigInteger bigInteger = new BigInteger(builder.toString());
        return bigInteger.mod(BigInteger.valueOf(MODULO));
    }

    private int transformInteger(int code){
        return code - START_ALPHANUMERICAL + 9;
    }
}
