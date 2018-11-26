package de.ostfalia.gruppe5.business.boundary.validation;

import java.math.BigInteger;

public class IBANValidatorHelper {

    private static final int MODULO = 97;
    private static final int COUNTRY_CODE_START_INDEX = 0;
    private static final int COUNTRY_CODE_LENGTH = 2;
    private static final int CHECK_DIGIT_START_INDEX = COUNTRY_CODE_START_INDEX + COUNTRY_CODE_LENGTH;
    private static final int CHECK_DIGIT_LENGTH = 2;
    private static final int BBAN_INDEX = CHECK_DIGIT_START_INDEX + CHECK_DIGIT_LENGTH;
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

    /**
     * IBAN Validation in four steps:
     *  1. Is the Country Code compliant to the national IBAN standard?
     *  2. Is the IBAN associated with the country code the correct length?
     *  3. Is the BBAN the correct length?
     *  4. If all the above is true, then do the calculation of mod 97 by appending the BBAN
     *      with the alphanumerical transformation into numbers and calculating mod 97.
     * @return true if all perquisites are met and the modified BBAN mod 97 is 1, else false.
     */
    public boolean validateIBAN(){
        if(!validateCountryCode()){
            return false;
        }else if(iban.length() != countryCode.getIbanLength()){
            return false;
        }else if(!validateBBAN()){
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
     * In the current Standard BBAN is always IBAN LENGTH - 4.
     * @return if the BBAN associated with the IBAN is the right size
     */
    private boolean validateBBAN(){
        return bban.length() == countryCode.getIbanLength()-4;
    }

    /**
     * CountryCode.valueOf is null if the country code enum does not exist.
     * If it does, then the country is compliant to the national IBAN format.
     * @return isValidCountryCode
     */
    private boolean validateCountryCode(){
        return countryCode != null;
    }

    /**
     * Transforms the country code for the mod97 validation explained at:
     * https://www.iban.de/iban-pruefsumme.html
     * @param code character of the country code as int value
     * @return transformed country code
     */
    private int transformCountryCode(int code){
        return code + 1 - 'A' + 9;
    }
}
