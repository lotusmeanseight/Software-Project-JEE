package de.ostfalia.gruppe5.business.boundary.validation;

import java.math.BigInteger;

public class IBANValidatorHelper {

    private static int MODULO = 97;
    private static int COUNTRY_CODE_START_INDEX = 0;
    private static int COUNTRY_CODE_LENGTH = 2;
    private static int CHECK_DIGIT_START_INDEX = COUNTRY_CODE_START_INDEX + COUNTRY_CODE_LENGTH;
    private static int CHECK_DIGIT_LENGTH = 2;
    private static int BBAN_INDEX = CHECK_DIGIT_START_INDEX + CHECK_DIGIT_LENGTH;
    private static int START_ALPHANUMERICAL = 'A';
    private final String IBAN;

    public IBANValidatorHelper(String IBAN){
        this.IBAN = IBAN;
    }

    public boolean validateIBAN(){
        int[] countryCodeToInt = IBAN.chars().limit(COUNTRY_CODE_LENGTH).map(this::transformCountryCode).toArray();
        StringBuilder builder = new StringBuilder();
        String checkDigits = IBAN.substring(CHECK_DIGIT_START_INDEX, CHECK_DIGIT_START_INDEX+CHECK_DIGIT_LENGTH);
        String BBAN = IBAN.substring(BBAN_INDEX);
        builder.append(BBAN);
        for (int a : countryCodeToInt) {
            builder.append(a);
        }
        builder.append(checkDigits);
        BigInteger bigInteger = new BigInteger(builder.toString());
        return bigInteger.mod(BigInteger.valueOf(MODULO)).equals(BigInteger.ONE);
    }

    private int transformCountryCode(int code){
        return code + 1 - START_ALPHANUMERICAL + 9;
    }

    public String getIBAN() {
        return IBAN;
    }
}
