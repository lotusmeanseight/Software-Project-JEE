package de.ostfalia.gruppe5.rest;

import org.junit.Before;

public class OfficeIT extends BasicIT<OfficeProxy, Integer, String> {
    @Before
    public void init() {
        this.setProxyType(OfficeProxy.class);
        this.setTestId(1);
        this.setPrimaryKey("officeCode");
        this.setTestEntity("{\"officeCode\":" + this.getPrimaryKeyToken() + "," +
                "\"city\":\"San Francisco\"," +
                "\"phone\":\"+1 650 219 4782\"," +
                "\"addressLine1\":\"100 Market Street\"," +
                "\"addressLine2\":\"Suite 300\"," +
                "\"state\":\"CA\"," +
                "\"country\":\"USA\"," +
                "\"postalCode\":" + this.getUpdateToken() + "," +
                "\"territory\":\"NA\"}");
        this.setUpdateKeyword("postalCode");
        this.setIdType(Integer.class);
        this.setUpdateType(String.class);
        this.setUpdateBase("100");
        this.setUpdateGoal("0");
    }
}
