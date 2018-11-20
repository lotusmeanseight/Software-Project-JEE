package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class ProductIT {
    @Test
    public void testGetSpecific() {
        String username = "Bow";
        String password = "1143";
        String id = "S10_1678";
        String target = "http://localhost:8080/sep-gruppe-5/api/products/" + id;

        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();

        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        Response response = web.request().get();
        JsonObject json = response.readEntity(JsonObject.class);
        json.keySet().stream().forEach(key -> System.out.println(key + ": " + json.get(key)));
        assertEquals(id, json.get("productCode").toString().replaceAll("\"", ""));
    }

    @Test
    public void testGetAll() {
        String username = "Bow";
        String password = "1143";
        String target = "http://localhost:8080/sep-gruppe-5/api/products/";

        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();

        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        Response response = web.request().get();
        JsonArray array = response.readEntity(JsonArray.class);
        array.stream().forEach(json -> {
            json.asJsonObject().keySet().stream().forEach(key -> System.out.println(key + ": " + json.asJsonObject().get(key)));
            System.out.println();
        });
    }
}
