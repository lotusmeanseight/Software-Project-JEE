package de.ostfalia.gruppe5.business.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "bankleitzahl")
public class Bankleitzahl {

	public Bankleitzahl() {
	}
	
	private String bankleitzahl;
	private String merkmal;
	private String bezeichnung;
	private String plz;
	private String ort;
	private String kurzbezeichnung;
	private String pan;
	private String bic;
	private String pruefzifferberechnungsmethode;
	private String datensatznummer;
	private String aenderungskennzeichen;
	private String bankleitzahlloeschung;
	private String nachfolgebankleitzahl;
	
	public String getBankleitzahl() {
		return bankleitzahl;
	}
	public void setBankleitzahl(String bankleitzahl) {
		this.bankleitzahl = bankleitzahl;
	}
	public String getMerkmal() {
		return merkmal;
	}
	public void setMerkmal(String merkmal) {
		this.merkmal = merkmal;
	}
	public String getBezeichnung() {
		return bezeichnung;
	}
	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public String getOrt() {
		return ort;
	}
	public void setOrt(String ort) {
		this.ort = ort;
	}
	public String getKurzbezeichnung() {
		return kurzbezeichnung;
	}
	public void setKurzbezeichnung(String kurzbezeichnung) {
		this.kurzbezeichnung = kurzbezeichnung;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public String getPruefzifferberechnungsmethode() {
		return pruefzifferberechnungsmethode;
	}
	public void setPruefzifferberechnungsmethode(String pruefzifferberechnungsmethode) {
		this.pruefzifferberechnungsmethode = pruefzifferberechnungsmethode;
	}
	public String getDatensatznummer() {
		return datensatznummer;
	}
	public void setDatensatznummer(String datensatznummer) {
		this.datensatznummer = datensatznummer;
	}
	public String getAenderungskennzeichen() {
		return aenderungskennzeichen;
	}
	public void setAenderungskennzeichen(String aenderungskennzeichen) {
		this.aenderungskennzeichen = aenderungskennzeichen;
	}
	public String getBankleitzahlloeschung() {
		return bankleitzahlloeschung;
	}
	public void setBankleitzahlloeschung(String bankleitzahlloeschung) {
		this.bankleitzahlloeschung = bankleitzahlloeschung;
	}
	public String getNachfolgebankleitzahl() {
		return nachfolgebankleitzahl;
	}
	public void setNachfolgebankleitzahl(String nachfolgebankleitzahl) {
		this.nachfolgebankleitzahl = nachfolgebankleitzahl;
	}
	
	
	
}
