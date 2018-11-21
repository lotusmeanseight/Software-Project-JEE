package de.ostfalia.gruppe5.rest;

import org.junit.Before;

public class EmployeeIT extends BasicIT<EmployeeProxy> {
    @Before
    public void init() {
        this.setProxyType(EmployeeProxy.class);
        this.setTestId("1702");
        this.setPrimaryKey("employeeNumber");
        this.setTestEntity("{\"employeeNumber\":"+this.getPrimaryKeyToken()+"," +
                "\"lastName\":\"Gerard\"," +
                "\"firstName\":\"Martin\"," +
                "\"extension\":\"x2312\"," +
                "\"email\":\""+this.getUpdateToken()+"\"," +
                "\"officeCode\":{\"officeCode\":4," +
                "\"city\":\"Paris\"," +
                "\"phone\":\"+33 14 723 4404\"," +
                "\"addressLine1\":\"43 Rue Jouffroy D'abbans\"," +
                "\"addressLine2\":null," +
                "\"state\":null," +
                "\"country\":\"France\"," +
                "\"postalCode\":\"75017\"," +
                "\"territory\":\"EMEA\"}," +
                "\"reportsTo\":1102," +
                "\"jobTitle\":\"Sales Rep\"}");
        this.setUpdateKeyword("email");
    }
}
