package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;

import static org.junit.Assert.assertEquals;

public class CustomerIT extends BasicIT<CustomerProxy> {
    @Before
    public void init() {
        this.setProxyType(CustomerProxy.class);
        this.setTestId("103");
        this.setPrimaryKey("customerNumber");
        this.setTestEntity("{\"employeeNumber\":"+this.getPrimaryKeyToken()+"," +
                "\"lastName\":\"Gerard\"," +
                "\"firstName\":\"Martin\"," +
                "\"extension\":\"x2312\"," +
                "\"email\":\""+this.getUpdateToken()+"\"," +
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
        this.setUpdateKeyword("customerName");
    }

    @Test
    public void testGetAssignedEmployee(){
        String id = this.getTestId();
        String expected = "{\"employeeNumber\":1370,\"lastName\":\"Hernandez\",\"firstName\":\"Gerard\",\"extension\":\"x2028\",\"email\":\"ghernande@classicmodelcars.com\",\"officeCode\":{\"officeCode\":4,\"city\":\"Paris\",\"phone\":\"+33 14 723 4404\",\"addressLine1\":\"43 Rue Jouffroy D'abbans\",\"addressLine2\":null,\"state\":null,\"country\":\"France\",\"postalCode\":\"75017\",\"territory\":\"EMEA\"},\"reportsTo\":1102,\"jobTitle\":\"Sales Rep\"}";
//        Create Product
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        EmployeeProxy employeeProxy = web.proxy(EmployeeProxy.class);
        JsonObject office = employeeProxy.getAssignedOffice(id);
        assertEquals(expected, office.toString());
    }
}
