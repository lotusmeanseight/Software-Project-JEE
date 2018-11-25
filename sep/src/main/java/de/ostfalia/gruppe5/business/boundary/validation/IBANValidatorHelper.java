package de.ostfalia.gruppe5.business.boundary.validation;

import java.math.BigInteger;

public class IBANValidatorHelper {

    private static final int MODULO = 97;
    private static final int COUNTRY_CODE_START_INDEX = 0;
    private static final int COUNTRY_CODE_LENGTH = 2;
    private static final int CHECK_DIGIT_START_INDEX = COUNTRY_CODE_START_INDEX + COUNTRY_CODE_LENGTH;
    private static final int CHECK_DIGIT_LENGTH = 2;
    private static final int BBAN_INDEX = CHECK_DIGIT_START_INDEX + CHECK_DIGIT_LENGTH;
    private static final int START_ALPHANUMERICAL = 'A';
    private final String iban;
    private final String bban;
    private final CountryCode countryCode;
    private final String checkDigits;

    public IBANValidatorHelper(String iban){
        this.iban = iban;
        this.bban = iban.substring(BBAN_INDEX);
        this.countryCode = CountryCode.valueOf(iban.substring(COUNTRY_CODE_START_INDEX, COUNTRY_CODE_LENGTH));
        this.checkDigits = iban.substring(CHECK_DIGIT_START_INDEX, CHECK_DIGIT_START_INDEX+CHECK_DIGIT_LENGTH);
    }

    public boolean validateIBAN(){
        if(iban.length() != countryCode.getIbanLength()){
            return false;
        }

        int[] countryCodeToInt = iban.chars().limit(COUNTRY_CODE_LENGTH).map(this::transformCountryCode).toArray();
        StringBuilder builder = new StringBuilder();
        builder.append(this.bban);

        for (int a : countryCodeToInt) {
            builder.append(a);
        }

        builder.append(this.checkDigits);
        BigInteger bigInteger = new BigInteger(builder.toString());
        return bigInteger.mod(BigInteger.valueOf(MODULO)).equals(BigInteger.ONE);
    }

    /**
     * BBAN is always IBAN LENGTH - 4.
     * if that changes, country code can hold individual info.
     * @return
     */
    public boolean validateBBAN(){
        return bban.length() == countryCode.getIbanLength()-4;
    }

    public boolean validateCountryCode(){
        return countryCode != null;
    }

    private int transformCountryCode(int code){
        return code + 1 - START_ALPHANUMERICAL + 9;
    }
}
