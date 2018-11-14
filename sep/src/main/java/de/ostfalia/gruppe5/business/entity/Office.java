package de.ostfalia.gruppe5.business.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NamedQueries({ @NamedQuery(name = "Office.countAll", query = "SELECT COUNT(o) FROM Office o"),
		@NamedQuery(name = "Office.findAll", query = "SELECT o FROM Office o") })
@Entity
@Table(name = "offices")
public class Office {

	@Id
	private Integer officeCode;

	@OneToMany(mappedBy = "officeCode", fetch = FetchType.LAZY)
	private List<Employee> employees = new ArrayList<>();

	@NotNull
	@Size(max = 50)
	private String city;

	@NotNull
	@Size(max = 50)
	private String phone;

	@NotNull
	@Size(max = 50)
	private String addressLine1;

	@Size(max = 50)
	private String addressLine2;

	@Size(max = 50)
	private String state;

	@NotNull
	@Size(max = 50)
	private String country;

	@NotNull
	@Size(max = 15)
	private String postalCode;

	@NotNull
	@Size(max = 10)
	private String territory;

	public Integer getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(Integer officeCode) {
		this.officeCode = officeCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Office office = (Office) o;
		return Objects.equals(getOfficeCode(), office.getOfficeCode()) && Objects.equals(employees, office.employees)
				&& Objects.equals(getCity(), office.getCity()) && Objects.equals(getPhone(), office.getPhone())
				&& Objects.equals(getAddressLine1(), office.getAddressLine1())
				&& Objects.equals(getAddressLine2(), office.getAddressLine2())
				&& Objects.equals(getState(), office.getState()) && Objects.equals(getCountry(), office.getCountry())
				&& Objects.equals(getPostalCode(), office.getPostalCode())
				&& Objects.equals(getTerritory(), office.getTerritory());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getOfficeCode(), employees, getCity(), getPhone(), getAddressLine1(), getAddressLine2(),
				getState(), getCountry(), getPostalCode(), getTerritory());
	}
}
