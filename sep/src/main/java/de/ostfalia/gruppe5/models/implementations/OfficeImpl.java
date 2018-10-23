package de.ostfalia.gruppe5.models.implementations;

import de.ostfalia.gruppe5.models.interfaces.Employee;
import de.ostfalia.gruppe5.models.interfaces.Office;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Office")
@Table(name = "offices")
public class OfficeImpl implements Office {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Size(max = 10)
    private String officeCode;

    @OneToMany(mappedBy = "officeCode", cascade = CascadeType.ALL, orphanRemoval = true)
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

    @Override
    public String getOfficeCode() {
        return officeCode;
    }

    @Override
    public void setOfficeCode(String officeCode) {
        this.officeCode = officeCode;
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
    public String getState() {
        return state;
    }

    @Override
    public void setState(String state) {
        this.state = state;
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
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String getTerritory() {
        return territory;
    }

    @Override
    public void setTerritory(String territory) {
        this.territory = territory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeImpl office = (OfficeImpl) o;
        return Objects.equals(getOfficeCode(), office.getOfficeCode()) &&
                Objects.equals(employees, office.employees) &&
                Objects.equals(getCity(), office.getCity()) &&
                Objects.equals(getPhone(), office.getPhone()) &&
                Objects.equals(getAddressLine1(), office.getAddressLine1()) &&
                Objects.equals(getAddressLine2(), office.getAddressLine2()) &&
                Objects.equals(getState(), office.getState()) &&
                Objects.equals(getCountry(), office.getCountry()) &&
                Objects.equals(getPostalCode(), office.getPostalCode()) &&
                Objects.equals(getTerritory(), office.getTerritory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOfficeCode(), employees, getCity(), getPhone(), getAddressLine1(), getAddressLine2(), getState(), getCountry(), getPostalCode(), getTerritory());
    }
}
