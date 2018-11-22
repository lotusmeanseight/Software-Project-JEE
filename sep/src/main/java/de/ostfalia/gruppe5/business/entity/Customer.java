package de.ostfalia.gruppe5.business.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NamedQueries({ @NamedQuery(name = "Customer.countAll", query = "SELECT COUNT(c) FROM Customer c"),
		@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c") })
@Entity
@Table(name = "customers")
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class Customer {

	@Id
	private Integer customerNumber;

	@OneToMany(mappedBy = "customerNumber", fetch = FetchType.LAZY)
	private List<Order> orders = new ArrayList<>();

	@OneToMany(mappedBy = "customerNumber", fetch = FetchType.LAZY)
	private List<Payment> payments = new ArrayList<>();

	@NotNull
	@Size(max = 50)
	private String customerName;

	@NotNull
	@Size(max = 50)
	private String contactLastName;

	@NotNull
	@Size(max = 50)
	private String contactFirstName;

	@NotNull
	@Size(max = 50)
	private String phone;

	@NotNull
	@Size(max = 50)
	private String addressLine1;

	@Size(max = 50)
	private String addressLine2;

	@NotNull
	@Size(max = 50)
	private String city;

	@Size(max = 50)
	private String state;

	@Size(max = 15)
	private String postalCode;

	@NotNull
	@Size(max = 50)
	private String country;

	@ManyToOne
	@JoinColumn(name = "salesRepEmployeeNumber")
	private Employee salesRepEmployeeNumber;

	private Double creditLimit;

	public Integer getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(Integer customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContactLastName() {
		return contactLastName;
	}

	public void setContactLastName(String contactLastName) {
		this.contactLastName = contactLastName;
	}

	public String getContactFirstName() {
		return contactFirstName;
	}

	public void setContactFirstName(String contactFirstName) {
		this.contactFirstName = contactFirstName;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Employee getSalesRepEmployeeNumber() {
		return salesRepEmployeeNumber;
	}

	public void setSalesRepEmployeeNumber(Employee salesRepEmployeeNumber) {
		this.salesRepEmployeeNumber = salesRepEmployeeNumber;
	}

	public Double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Double creditLimit) {
		this.creditLimit = creditLimit;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Customer customer = (Customer) o;
		return Objects.equals(getCustomerNumber(), customer.getCustomerNumber())
				&& Objects.equals(orders, customer.orders) && Objects.equals(payments, customer.payments)
				&& Objects.equals(getCustomerName(), customer.getCustomerName())
				&& Objects.equals(getContactLastName(), customer.getContactLastName())
				&& Objects.equals(getContactFirstName(), customer.getContactFirstName())
				&& Objects.equals(getPhone(), customer.getPhone())
				&& Objects.equals(getAddressLine1(), customer.getAddressLine1())
				&& Objects.equals(getAddressLine2(), customer.getAddressLine2())
				&& Objects.equals(getCity(), customer.getCity()) && Objects.equals(getState(), customer.getState())
				&& Objects.equals(getPostalCode(), customer.getPostalCode())
				&& Objects.equals(getCountry(), customer.getCountry())
				&& Objects.equals(getSalesRepEmployeeNumber(), customer.getSalesRepEmployeeNumber())
				&& Objects.equals(getCreditLimit(), customer.getCreditLimit());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getCustomerNumber(), orders, payments, getCustomerName(), getContactLastName(),
				getContactFirstName(), getPhone(), getAddressLine1(), getAddressLine2(), getCity(), getState(),
				getPostalCode(), getCountry(), getSalesRepEmployeeNumber(), getCreditLimit());
	}

}