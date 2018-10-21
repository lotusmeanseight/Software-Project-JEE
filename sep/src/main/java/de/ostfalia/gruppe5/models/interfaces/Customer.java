package de.ostfalia.gruppe5.models.interfaces;

public interface Customer {
    Integer getCustomerNumber();

    void setCustomerNumber(Integer customerNumber);

    String getCustomerName();

    void setCustomerName(String customerName);

    String getContactLastName();

    void setContactLastName(String contactLastName);

    String getContactFirstName();

    void setContactFirstName(String contactFirstName);

    String getPhone();

    void setPhone(String phone);

    String getAddressLine1();

    void setAddressLine1(String addressLine1);

    String getAddressLine2();

    void setAddressLine2(String addressLine2);

    String getCity();

    void setCity(String city);

    String getState();

    void setState(String state);

    String getPostalCode();

    void setPostalCode(String postalCode);

    String getCountry();

    void setCountry(String country);

    Employee getSalesRepEmployeeNumber();

    void setSalesRepEmployeeNumber(Employee salesRepEmployeeNumber);

    Double getCreditLimit();

    void setCreditLimit(Double creditLimit);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
