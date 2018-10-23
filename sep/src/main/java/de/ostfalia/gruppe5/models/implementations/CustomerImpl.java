package de.ostfalia.gruppe5.models.implementations;

import de.ostfalia.gruppe5.models.interfaces.Customer;
import de.ostfalia.gruppe5.models.interfaces.Employee;
import de.ostfalia.gruppe5.models.interfaces.Order;
import de.ostfalia.gruppe5.models.interfaces.Payment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Customer")
@Table(name = "customers")
public class CustomerImpl implements Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerNumber;

    @OneToMany(mappedBy = "customerNumber", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "customerNumber", cascade = CascadeType.ALL, orphanRemoval = true)
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

    @Override
    public Integer getCustomerNumber() {
        return customerNumber;
    }

    @Override
    public void setCustomerNumber(Integer customerNumber) {
        this.customerNumber = customerNumber;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String getContactLastName() {
        return contactLastName;
    }

    @Override
    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    @Override
    public String getContactFirstName() {
        return contactFirstName;
    }

    @Override
    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getAddressLine1() {
        return addressLine1;
    }

    @Override
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    @Override
    public String getAddressLine2() {
        return addressLine2;
    }

    @Override
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String getCountry() {
        return country;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public Employee getSalesRepEmployeeNumber() {
        return salesRepEmployeeNumber;
    }

    @Override
    public void setSalesRepEmployeeNumber(Employee salesRepEmployeeNumber) {
        this.salesRepEmployeeNumber = salesRepEmployeeNumber;
    }

    @Override
    public Double getCreditLimit() {
        return creditLimit;
    }

    @Override
    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerImpl customer = (CustomerImpl) o;
        return Objects.equals(getCustomerNumber(), customer.getCustomerNumber()) &&
                Objects.equals(orders, customer.orders) &&
                Objects.equals(payments, customer.payments) &&
                Objects.equals(getCustomerName(), customer.getCustomerName()) &&
                Objects.equals(getContactLastName(), customer.getContactLastName()) &&
                Objects.equals(getContactFirstName(), customer.getContactFirstName()) &&
                Objects.equals(getPhone(), customer.getPhone()) &&
                Objects.equals(getAddressLine1(), customer.getAddressLine1()) &&
                Objects.equals(getAddressLine2(), customer.getAddressLine2()) &&
                Objects.equals(getCity(), customer.getCity()) &&
                Objects.equals(getState(), customer.getState()) &&
                Objects.equals(getPostalCode(), customer.getPostalCode()) &&
                Objects.equals(getCountry(), customer.getCountry()) &&
                Objects.equals(getSalesRepEmployeeNumber(), customer.getSalesRepEmployeeNumber()) &&
                Objects.equals(getCreditLimit(), customer.getCreditLimit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerNumber(), orders, payments, getCustomerName(), getContactLastName(), getContactFirstName(), getPhone(), getAddressLine1(), getAddressLine2(), getCity(), getState(), getPostalCode(), getCountry(), getSalesRepEmployeeNumber(), getCreditLimit());
    }
}