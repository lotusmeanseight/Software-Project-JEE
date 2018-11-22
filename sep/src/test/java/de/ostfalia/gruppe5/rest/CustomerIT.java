package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;

import static org.junit.Assert.assertEquals;

public class CustomerIT extends BasicIT<CustomerProxy, Integer> {
    @Before
    public void init() {
        this.setProxyType(CustomerProxy.class);
        this.setTestId(103);
        this.setPrimaryKey("customerNumber");
        this.setTestEntity("{\"customerNumber\":"+this.getPrimaryKeyToken()+",\"customerName\":\""+this.getUpdateToken()+"\",\"contactLastName\":\"King\",\"contactFirstName\":\"Jean\",\"phone\":\"7025551838\",\"addressLine1\":\"8489 Strong St.\",\"addressLine2\":null,\"city\":\"Las Vegas\",\"state\":\"NV\",\"postalCode\":\"83030\",\"country\":\"USA\",\"salesRepEmployeeNumber\":{\"employeeNumber\":1166,\"lastName\":\"Thompson\",\"firstName\":\"Leslie\",\"extension\":\"x4065\",\"email\":\"lthompson@classicmodelcars.com\",\"officeCode\":{\"officeCode\":1,\"city\":\"San Francisco\",\"phone\":\"+1 650 219 4782\",\"addressLine1\":\"100 Market Street\",\"addressLine2\":\"Suite 300\",\"state\":\"CA\",\"country\":\"USA\",\"postalCode\":\"94080\",\"territory\":\"NA\"},\"reportsTo\":1143,\"jobTitle\":\"Sales Rep\"},\"creditLimit\":71800.0}");
        this.setUpdateKeyword("customerName");
        this.setIdType(Integer.class);
    }

    @Test
    public void testGetAssignedEmployee(){
        Integer id = this.getTestId();
        String expected = "{\"employeeNumber\":1370,\"lastName\":\"Hernandez\",\"firstName\":\"Gerard\",\"extension\":\"x2028\",\"email\":\"ghernande@classicmodelcars.com\",\"officeCode\":{\"officeCode\":4,\"city\":\"Paris\",\"phone\":\"+33 14 723 4404\",\"addressLine1\":\"43 Rue Jouffroy D'abbans\",\"addressLine2\":null,\"state\":null,\"country\":\"France\",\"postalCode\":\"75017\",\"territory\":\"EMEA\"},\"reportsTo\":1102,\"jobTitle\":\"Sales Rep\"}";
//        Create Product
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        CustomerProxy customerProxy = web.proxy(CustomerProxy.class);
        JsonObject employee = customerProxy.getAssignedEmployee(id);
        assertEquals(expected, employee.toString());
    }
}
