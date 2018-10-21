package de.ostfalia.gruppe5.models.interfaces;

public interface Office {
    String getOfficeCode();

    void setOfficeCode(String officeCode);

    String getCity();

    void setCity(String city);

    String getPhone();

    void setPhone(String phone);

    String getAddressLine1();

    void setAddressLine1(String addressLine1);

    String getAddressLine2();

    void setAddressLine2(String addressLine2);

    String getState();

    void setState(String state);

    String getCountry();

    void setCountry(String country);

    String getPostalCode();

    void setPostalCode(String postalCode);

    String getTerritory();

    void setTerritory(String territory);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();
}
