package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offices")
public class Office {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Size(max=10)
	private String officeCode;

    @OneToMany(mappedBy = "officeCode", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

	@NotNull
	@Size(max=50)
	private String city;
	
	@NotNull
	@Size(max=50)
	private String phone;
	
	@NotNull
	@Size(max=50)
	private String addressLine1;
	
	@Size(max=50)
	private String addressLine2;
	
	@Size(max=50)
	private String state;
	
	@NotNull
	@Size(max=50)
	private String country;
	
	@NotNull
	@Size(max=15)
	private String postalCode;
	
	@NotNull
	@Size(max=10)
	private String territory;

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
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
	
	
	
}
