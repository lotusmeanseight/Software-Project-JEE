package de.ostfalia.gruppe5.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeNumber;

    @OneToMany(mappedBy = "salesRepEmployeeNumber", fetch = FetchType.LAZY)
    private List<Customer> customers = new ArrayList<>();

    @NotNull
    @Size(max=50)
    private String lastName;

    @NotNull
    @Size(max=50)
    private String firstName;

    @NotNull
    @Size(max=10)
    private String extension;

    @NotNull
    @Size(max=100)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "officeCode")
    private Office officeCode;

    private Integer reportsTo;

    @NotNull
    @Size(max=50)
    private String jobTitle;


    public void addCustomer(Customer customer) {
        if (customers.contains(customer)) {
            return;
        }

        customers.add(customer);
        customer.setSalesRepEmployeeNumber(this);
    }

    public void removeCustomer(Customer customer) {
        if (!customers.contains(customer)) {
            return;
        }
        customers.remove(customer);
        customer.setSalesRepEmployeeNumber(null);
    }


    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Office getOfficeCode() {
        return officeCode;
    }

    public void setOfficeCode(Integer officeCode) {
        this.officeCode.setOfficeCode(officeCode);
    }

    public Integer getReportsTo() {
        return reportsTo;
    }

    public void setReportsTo(Integer reportsTo) {
        this.reportsTo = reportsTo;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getEmployeeNumber(), employee.getEmployeeNumber()) &&
                Objects.equals(customers, employee.customers) &&
                Objects.equals(getLastName(), employee.getLastName()) &&
                Objects.equals(getFirstName(), employee.getFirstName()) &&
                Objects.equals(getExtension(), employee.getExtension()) &&
                Objects.equals(getEmail(), employee.getEmail()) &&
                Objects.equals(getOfficeCode(), employee.getOfficeCode()) &&
                Objects.equals(getReportsTo(), employee.getReportsTo()) &&
                Objects.equals(getJobTitle(), employee.getJobTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeNumber(), customers, getLastName(), getFirstName(), getExtension(), getEmail(), getOfficeCode(), getReportsTo(), getJobTitle());
    }
}
