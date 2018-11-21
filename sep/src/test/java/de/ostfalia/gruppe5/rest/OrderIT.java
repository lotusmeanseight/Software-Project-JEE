package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;

import static org.junit.Assert.assertEquals;

public class OrderIT extends BasicIT<OrderProxy> {
    @Before
    public void init() {
        this.setProxyType(OrderProxy.class);
        this.setTestId("10100");
        this.setPrimaryKey("orderNumber");
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
        this.setUpdateKeyword("comments");
    }

    @Test
    public void testGetAssignedCustomer(){
        String id = this.getTestId();
        String expected = "{\"officeCode\":4,\"city\":\"Paris\",\"phone\":\"+33 14 723 4404\",\"addressLine1\":\"43 Rue Jouffroy D'abbans\",\"addressLine2\":null,\"state\":null,\"country\":\"France\",\"postalCode\":\"75017\",\"territory\":\"EMEA\"}";
//        Create Product
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        OrderProxy employeeProxy = web.proxy(OrderProxy.class);
        JsonObject office = employeeProxy.getAssignedCustomer(id);

        assertEquals(expected, office.toString());
    }

    @Test
    public void testGetOrderDetails(){
        String id = this.getTestId();
        String expected = "{\"officeCode\":4,\"city\":\"Paris\",\"phone\":\"+33 14 723 4404\",\"addressLine1\":\"43 Rue Jouffroy D'abbans\",\"addressLine2\":null,\"state\":null,\"country\":\"France\",\"postalCode\":\"75017\",\"territory\":\"EMEA\"}";
//        Create Product
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        OrderProxy employeeProxy = web.proxy(OrderProxy.class);
        JsonObject office = employeeProxy.getOrderDetails(id);

        assertEquals(expected, office.toString());
    }
}
