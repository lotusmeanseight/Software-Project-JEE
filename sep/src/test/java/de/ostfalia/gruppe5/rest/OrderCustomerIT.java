package de.ostfalia.gruppe5.rest;

import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Test;

import javax.json.*;
import javax.ws.rs.core.Response;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderCustomerIT {
    private String username = "King";
    private String password = "112";
    private String target = "http://localhost:8080/sep-gruppe-5/api";

    @Test
    public void testGetAll() {
        Integer[] expected = new Integer[]{10180, 10224};
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.target);
        web.register(new BasicAuthentication("Ranc\\u00e9", "171"));
        OrderProxy orderProxy = web.proxy(OrderProxy.class);
        JsonArray customerOrders = orderProxy.getAllEntities();
        System.out.println(customerOrders.toString());
        System.out.println(customerOrders.size());
        for (int i = 0; i < customerOrders.size(); i++) {
            assertTrue(expected[i] == customerOrders.get(i).asJsonObject().getInt("orderNumber"));
        }
    }

    @Test
    public void testPostOrderBasket() {
        Integer[] expected = new Integer[]{10124, 10278, 10346};
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.target);
        web.register(new BasicAuthentication(this.username, this.password));
        OrderProxy orderProxy = web.proxy(OrderProxy.class);
        String jsonString = "{\"iban\":\"DE08700901001234567890\",\"kntnr\":\"\",\"blz\":\"\",\"orders\":[{\"productCode\":\"S12_1099\",\"amount\":3}]}";
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        Response response = orderProxy.postOrderBasket(jsonObject);
        assertTrue("Location can not be read", response.getLocation() != null);
        assertEquals(201, response.getStatus());
    }
}
