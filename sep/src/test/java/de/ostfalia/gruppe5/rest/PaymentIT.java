package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PaymentIT extends BasicIT<PaymentProxy, String, BigDecimal> {
    @Before
    public void init() {
        this.setProxyType(PaymentProxy.class);
        this.setTestId("HQ336336");
        this.setPrimaryKey("checkNumber");
        this.setUpdateKeyword("amount");
        this.setTestEntity("" +
                "{" +
                "\"customerNumber\":" +
                "{" +
                "\"customerNumber\":112," +
                "\"customerName\":\"Signal Gift Stores\"," +
                "\"contactLastName\":\"King\"," +
                "\"contactFirstName\":\"Jean\"," +
                "\"phone\":\"7025551838\"," +
                "\"addressLine1\":\"8489 Strong St.\"," +
                "\"addressLine2\":null," +
                "\"city\":\"Las Vegas\"," +
                "\"state\":\"NV\"," +
                "\"postalCode\":\"83030\"," +
                "\"country\":\"USA\"," +
                "\"salesRepEmployeeNumber\":" +
                "{" +
                "\"employeeNumber\":1166," +
                "\"lastName\":\"Thompson\"," +
                "\"firstName\":\"Leslie\"," +
                "\"extension\":\"x4065\"," +
                "\"email\":\"lthompson@classicmodelcars.com\"," +
                "\"officeCode\":" +
                "{" +
                "\"officeCode\":1," +
                "\"city\":\"San Francisco\"," +
                "\"phone\":\"+1 650 219 4782\"," +
                "\"addressLine1\":\"100 Market Street\"," +
                "\"addressLine2\":\"Suite 300\"," +
                "\"state\":\"CA\"," +
                "\"country\":\"USA\"," +
                "\"postalCode\":\"94080\"," +
                "\"territory\":\"NA\"}," +
                "\"reportsTo\":1143," +
                "\"jobTitle\":\"Sales Rep\"}," +
                "\"creditLimit\":71800.0}," +
                "\"checkNumber\":" + this.getPrimaryKeyToken() + "," +
                "\"paymentDate\":" +
                "{" +
                "\"year\":2003," +
                "\"month\":\"JUNE\"," +
                "\"era\":\"CE\"," +
                "\"dayOfYear\":157," +
                "\"dayOfWeek\":\"FRIDAY\"," +
                "\"leapYear\":false," +
                "\"dayOfMonth\":6," +
                "\"monthValue\":6," +
                "\"chronology\":" +
                "{" +
                "\"calendarType\":\"iso8601\"," +
                "\"id\":\"ISO\"" +
                "}" +
                "}," +
                "\"amount\":"+this.getUpdateToken()+"}");
        this.setIdType(String.class);
        this.setUpdateType(BigDecimal.class);
        this.setUpdateBase(new BigDecimal(100.5));
        this.setUpdateGoal(new BigDecimal(0.5));
    }

    @Test
    public void testGetAssignedCustomer() {
        String expected = "{\"customerNumber\":112,\"customerName\":\"Signal Gift Stores\",\"contactLastName\":\"King\",\"contactFirstName\":\"Jean\",\"phone\":\"7025551838\",\"addressLine1\":\"8489 Strong St.\",\"addressLine2\":null,\"city\":\"Las Vegas\",\"state\":\"NV\",\"postalCode\":\"83030\",\"country\":\"USA\",\"salesRepEmployeeNumber\":{\"employeeNumber\":1166,\"lastName\":\"Thompson\",\"firstName\":\"Leslie\",\"extension\":\"x4065\",\"email\":\"lthompson@classicmodelcars.com\",\"officeCode\":{\"officeCode\":1,\"city\":\"San Francisco\",\"phone\":\"+1 650 219 4782\",\"addressLine1\":\"100 Market Street\",\"addressLine2\":\"Suite 300\",\"state\":\"CA\",\"country\":\"USA\",\"postalCode\":\"94080\",\"territory\":\"NA\"},\"reportsTo\":1143,\"jobTitle\":\"Sales Rep\"},\"creditLimit\":71800.0}";
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        PaymentProxy paymentProxy = web.proxy(PaymentProxy.class);
        JsonObject office = paymentProxy.getAssignedCustomer(this.getTestId());

        assertEquals(expected, office.toString());
    }
}
