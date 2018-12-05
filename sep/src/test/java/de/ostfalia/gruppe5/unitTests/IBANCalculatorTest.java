package de.ostfalia.gruppe5.unitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.ostfalia.gruppe5.business.controller.IBANCalculator;

public class IBANCalculatorTest {
	
	private final String kontonummer = "1234567890";
	private final String blz = "70090100";
	
	private final String ibanTo = "DE08700901001234567890";
	
	@Test
	public void test1() {
		
		String iban = IBANCalculator.calculateDEIBANFromKntnrAndBlz(kontonummer, blz);
		assertTrue((iban.equals(ibanTo)));
	}

}
