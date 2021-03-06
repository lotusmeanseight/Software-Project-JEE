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
        this.setTestEntity("{\"customerNumber\":{\"customerNumber\":103,\"customerName\":\"Atelier graphique\",\"contactLastName\":\"Schmitt\",\"contactFirstName\":\"Carine \",\"phone\":\"40.32.2555\",\"addressLine1\":\"54, rue Royale\",\"addressLine2\":null,\"city\":\"Nantes\",\"state\":null,\"postalCode\":\"44000\",\"country\":\"France\",\"salesRepEmployeeNumber\":{\"employeeNumber\":1370,\"lastName\":\"Hernandez\",\"firstName\":\"Gerard\",\"extension\":\"x2028\",\"email\":\"ghernande@classicmodelcars.com\",\"officeCode\":{\"officeCode\":4,\"city\":\"Paris\",\"phone\":\"+33 14 723 4404\",\"addressLine1\":\"43 Rue Jouffroy D'abbans\",\"addressLine2\":null,\"state\":null,\"country\":\"France\",\"postalCode\":\"75017\",\"territory\":\"EMEA\"},\"reportsTo\":1102,\"jobTitle\":\"Sales Rep\"},\"creditLimit\":21000.0},\"checkNumber\":"+this.getPrimaryKeyToken()+",\"paymentDate\":{\"year\":2004,\"month\":\"OCTOBER\",\"era\":\"CE\",\"dayOfYear\":293,\"dayOfWeek\":\"TUESDAY\",\"leapYear\":true,\"dayOfMonth\":19,\"monthValue\":10,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"amount\":"+this.getUpdateToken()+"}");
        this.setIdType(String.class);
        this.setUpdateType(BigDecimal.class);
        this.setUpdateBase(new BigDecimal(100.5));
        this.setUpdateGoal(new BigDecimal(0.5));
    }

    @Test
    public void testGetAssignedCustomer() {
        String expected = "{\"customerNumber\":103,\"customerName\":\"Atelier graphique\",\"contactLastName\":\"Schmitt\",\"contactFirstName\":\"Carine \",\"phone\":\"40.32.2555\",\"addressLine1\":\"54, rue Royale\",\"addressLine2\":null,\"city\":\"Nantes\",\"state\":null,\"postalCode\":\"44000\",\"country\":\"France\",\"salesRepEmployeeNumber\":{\"employeeNumber\":1370,\"lastName\":\"Hernandez\",\"firstName\":\"Gerard\",\"extension\":\"x2028\",\"email\":\"ghernande@classicmodelcars.com\",\"officeCode\":{\"officeCode\":4,\"city\":\"Paris\",\"phone\":\"+33 14 723 4404\",\"addressLine1\":\"43 Rue Jouffroy D'abbans\",\"addressLine2\":null,\"state\":null,\"country\":\"France\",\"postalCode\":\"75017\",\"territory\":\"EMEA\"},\"reportsTo\":1102,\"jobTitle\":\"Sales Rep\"},\"creditLimit\":21000.0}";
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        PaymentProxy paymentProxy = web.proxy(PaymentProxy.class);
        JsonObject office = paymentProxy.getAssignedCustomer(this.getTestId());

        assertEquals(expected, office.toString());
    }
}
