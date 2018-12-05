package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Test;

import javax.json.JsonArray;

import static org.junit.Assert.assertTrue;

public class OrderCustomerIT {
    private String username = "King";
    private String password = "112";
    private String target = "http://localhost:8080/sep-gruppe-5/api";

    @Test
    public void testGetAll() {
        Integer[] expected = new Integer[]{10124,10278,10346};
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.target);
        web.register(new BasicAuthentication(this.username, this.password));
        OrderProxy orderProxy = web.proxy(OrderProxy.class);
        JsonArray customerOrders = orderProxy.getAllEntities();
        for (int i = 0; i < customerOrders.size(); i++) {
            assertTrue(expected[i]==customerOrders.get(i).asJsonObject().getInt("orderNumber"));
        }
    }
}
