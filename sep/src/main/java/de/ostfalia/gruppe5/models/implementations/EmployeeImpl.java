package de.ostfalia.gruppe5.models.implementations;

import de.ostfalia.gruppe5.models.interfaces.Customer;
import de.ostfalia.gruppe5.models.interfaces.Employee;
import de.ostfalia.gruppe5.models.interfaces.Office;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Employee")
@Table(name = "employees")
public class EmployeeImpl implements Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeNumber;

    @OneToMany(mappedBy = "salesRepEmployeeNumber", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Customer> customers = new ArrayList<>();

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 10)
    private String extension;

    @NotNull
    @Size(max = 100)
    private String email;

    @NotNull
    @Size(max = 10)
    @ManyToOne
    @JoinColumn(name = "officeCode")
    private Office officeCode;

    private Integer reportsTo;

    @NotNull
    @Size(max = 50)
    private String jobTitle;

    @Override
    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    @Override
    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getExtension() {
        return extension;
    }

    @Override
    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Office getOfficeCode() {
        return officeCode;
    }

    @Override
    public void setOfficeCode(Office officeCode) {
        this.officeCode = officeCode;
    }

    @Override
    public Integer getReportsTo() {
        return reportsTo;
    }

    @Override
    public void setReportsTo(Integer reportsTo) {
        this.reportsTo = reportsTo;
    }

    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }

    @Override
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getEmployeeNumber(), employee.getEmployeeNumber()) &&
                Objects.equals(this.customers, employee.getCustomers()) &&
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
