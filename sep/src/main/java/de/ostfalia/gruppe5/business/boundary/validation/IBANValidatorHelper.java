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
    private String BBAN;
    private String countryCode;
    private String checkDigits;

    public IBANValidatorHelper(String IBAN){
        this.IBAN = IBAN;
        this.BBAN = IBAN.substring(BBAN_INDEX);
        this.countryCode = IBAN.substring(COUNTRY_CODE_START_INDEX, COUNTRY_CODE_LENGTH-1);
        this.checkDigits = IBAN.substring(CHECK_DIGIT_START_INDEX, CHECK_DIGIT_START_INDEX+CHECK_DIGIT_LENGTH);
    }

    public boolean validateIBAN(){
        int[] countryCodeToInt = IBAN.chars().limit(COUNTRY_CODE_LENGTH).map(this::transformCountryCode).toArray();
        StringBuilder builder = new StringBuilder();
        builder.append(this.BBAN);
        for (int a : countryCodeToInt) {
            builder.append(a);
        }
        builder.append(this.checkDigits);
        BigInteger bigInteger = new BigInteger(builder.toString());
        return bigInteger.mod(BigInteger.valueOf(MODULO)).equals(BigInteger.ONE);
    }

    public boolean validateBBAN(){
        return false; //TODO
    }

    public boolean validateCountryCode(){
        return false; //TODO
    }

    private int transformCountryCode(int code){
        return code + 1 - START_ALPHANUMERICAL + 9;
    }

    public String getIBAN() {
        return IBAN;
    }
}
