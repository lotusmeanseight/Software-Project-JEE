package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;

import static org.junit.Assert.assertEquals;

public class EmployeeIT extends BasicIT<EmployeeProxy, Integer, String> {
    @Before
    public void init() {
        this.setProxyType(EmployeeProxy.class);
        this.setTestId(1702);
        this.setPrimaryKey("employeeNumber");
        this.setTestEntity("{\"employeeNumber\":" + this.getPrimaryKeyToken() + "," +
                "\"lastName\":\"Gerard\"," +
                "\"firstName\":\"Martin\"," +
                "\"extension\":\"x2312\"," +
                "\"email\":" + this.getUpdateToken() + "," +
                "\"officeCode\":" +
                "{" +
                "\"officeCode\":4," +
                "\"city\":\"Paris\"," +
                "\"phone\":\"+33 14 723 4404\"," +
                "\"addressLine1\":\"43 Rue Jouffroy D'abbans\"," +
                "\"addressLine2\":null," +
                "\"state\":null," +
                "\"country\":\"France\"," +
                "\"postalCode\":\"75017\"," +
                "\"territory\":\"EMEA\"" +
                "}," +
                "\"reportsTo\":1102," +
                "\"jobTitle\":\"Sales Rep\"}");
        this.setUpdateKeyword("email");
        this.setIdType(Integer.class);
        this.setUpdateType(String.class);
        this.setUpdateBase("100");
        this.setUpdateGoal("0");
    }

    @Test
    public void testGetAssignedOffice() {
        String expected = "{\"officeCode\":4,\"city\":\"Paris\",\"phone\":\"+33 14 723 4404\",\"addressLine1\":\"43 Rue Jouffroy D'abbans\",\"addressLine2\":null,\"state\":null,\"country\":\"France\",\"postalCode\":\"75017\",\"territory\":\"EMEA\"}";
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        EmployeeProxy employeeProxy = web.proxy(EmployeeProxy.class);
        JsonObject office = employeeProxy.getAssignedOffice(this.getTestId());
        assertEquals(expected, office.toString());
    }
}
