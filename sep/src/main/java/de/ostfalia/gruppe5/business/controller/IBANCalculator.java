package de.ostfalia.gruppe5.business.controller;

import de.ostfalia.gruppe5.business.boundary.validation.CountryCode;

public class IBANCalculator {
	
	public static String calculateDEIBANFromKntnrAndBlz(String kntnr, String blz) {

		String bban = blz + kntnr;
		String lCode = CountryCode.DE.toString();
		String lCodeZiffern = "131400";
		
		int pruefziffer = Integer.parseInt(bban + lCodeZiffern) % 97;
		
		String pruefzifferString;
		if(pruefziffer < 10) {
			pruefzifferString = "0" + pruefziffer;
		} else {
			pruefzifferString = Integer.toString(pruefziffer);
		}
		
		return lCode + pruefzifferString + bban;
	}

}
