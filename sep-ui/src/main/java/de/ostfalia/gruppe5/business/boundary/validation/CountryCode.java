package de.ostfalia.gruppe5.business.boundary.validation;

/**
 * All Countries that are included in
 * https://www.swift.com/standards/data-standards/iban ISO 13616-Compliant IBAN
 * Formats
 */
public enum CountryCode {

	AD("Andorra", 24), AE("United Arab Emirates", 23), AL("Albania", 28), AT("Austria", 20), AZ("Azerbaijan", 28),
	BA("Bosnia and Herzegovania", 20), BE("Belgium", 16), BG("Bulgaria", 22), BH("Bahrain", 22), BR("Brazil", 29),
	BY("Republic of Belarus", 28), CH("Switzerland", 21), CR("Costa Rica", 22), CY("Cyprus", 28),
	CZ("Czech Republic", 24), DE("Germany", 22), DK("Denmark", 18), DO("Domanian Republic", 28), EE("Estonia", 20),
	ES("Spain", 24), FI("Finland", 18), FO("Faroe Islands", 18), FR("France", 27), GB("United Kingdom", 22),
	GE("Georgia", 22), GI("Gibraltar", 23), GL("Greenland", 18), GR("Greece", 27), GT("Guatemala", 28),
	HR("Croatia", 21), HU("Hungary", 28), IE("Ireland", 22), IL("Israel", 23), IQ("Iraq", 23), IS("Iceland", 26),
	IT("Italy", 27), JO("Jordan", 30), KW("Kuwait", 30), KZ("Kazakhstan", 20), LB("Lebanon", 28), LC("Saint Lucia", 32),
	LI("Liechtenstein", 21), LT("Lithuania", 20), LU("Luxembourg", 20), LV("Latvia", 21), MC("Monaco", 27),
	MD("Moldova", 24), ME("Montenegro", 22), MK("Macedonia", 19), MR("Mauritania", 27), MT("Malta", 31),
	MU("Mauritius", 30), NL("Netherlands", 18), NO("Norway", 15), PK("Pakistan", 24), PL("Poland", 28),
	PS("Palestine", 29), PT("Portugal", 25), QA("Qatar", 29), RO("Romania", 24), RS("Serbia", 22),
	SA("Saudi Arabia", 24), SC("Seychelles", 31), SE("Sweden", 24), SI("Slovenia", 19), SK("Slovakia", 24),
	SM("San Marino", 23), ST("Sao Tome and Principe", 25), SV("El Salvador", 28), TL("Timor-Leste", 23),
	TN("Tunisia", 24), TR("Turkey", 26), UA("Ukraine", 29), VA("Vatican City State", 22), VG("Virgin Islands", 24),
	XK("Kosovo", 20);

	private final String countryName;
	private final int ibanLength;

	CountryCode(String countryName, int ibanLength) {
		this.countryName = countryName;
		this.ibanLength = ibanLength;
	}

	public String getCountryName() {
		return countryName;
	}

	public int getIbanLength() {
		return ibanLength;
	}

}
