package de.ostfalia.gruppe5.business.controller;

import java.math.BigInteger;

import javax.faces.convert.BigIntegerConverter;

import de.ostfalia.gruppe5.business.boundary.validation.CountryCode;

public class IBANCalculator {
	
	public static String calculateDEIBANFromKntnrAndBlz(String kntnr, String blz) {

		String bban = blz + kntnr;
		String lCode = CountryCode.DE.toString();
		String lCodeZiffern = "131400";
		
		String full = bban + lCodeZiffern;
		BigInteger testBan = new BigInteger(full);
		BigInteger siebenUndNeunzig = new BigInteger("97");
		BigInteger pruefzifferBigInt = testBan.mod(siebenUndNeunzig);
		int pruefziffer = pruefzifferBigInt.intValue();
		pruefziffer = 98 - 90;
		String pruefzifferString;
		if(pruefziffer < 10) {
			pruefzifferString = "0" + pruefziffer;
		} else {
			pruefzifferString = Integer.toString(pruefziffer);
		}
		
		return lCode + pruefzifferString + bban;
	}

}
