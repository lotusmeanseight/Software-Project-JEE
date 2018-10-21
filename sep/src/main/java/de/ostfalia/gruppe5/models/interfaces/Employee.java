package de.ostfalia.gruppe5.models.interfaces;

import java.util.List;

public interface Employee {
    Integer getEmployeeNumber();

    void setEmployeeNumber(Integer employeeNumber);

    String getLastName();

    void setLastName(String lastName);

    String getFirstName();

    void setFirstName(String firstName);

    String getExtension();

    void setExtension(String extension);

    String getEmail();

    void setEmail(String email);

    Office getOfficeCode();

    void setOfficeCode(Office officeCode);

    Integer getReportsTo();

    void setReportsTo(Integer reportsTo);

    String getJobTitle();

    void setJobTitle(String jobTitle);

    List<Customer> getCustomers();

    void setCustomers(List<Customer> customers);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
