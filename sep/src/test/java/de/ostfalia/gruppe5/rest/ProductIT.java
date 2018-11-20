package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductIT {

    private final String productCode = "productCode";
    private final String username = "Bow";
    private final String password = "1143";
    private final String id = "S10_1678";
    private final String target = "http://localhost:8080/sep-gruppe-5/api/products/";

    @Test
    public void testSepAuthGetAll() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target + id);
        Response response = web.request(MediaType.APPLICATION_JSON).header("SEP_AUTHORIZATION", username + ":" + password).get();
        assertEquals(200,response.getStatus());
        String json = response.readEntity(String.class);
        System.out.println(json);
//        json.keySet().stream().forEach(key -> System.out.println(key + ": " + json.get(key)));
//        assertEquals(id, json.get("productCode").toString().replaceAll("\"", ""));
    }

    @Test
    public void testGetSpecific() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target + id);
        web.register(new BasicAuthentication(username, password));
        Response response = web.request(MediaType.APPLICATION_JSON).get();
        assertEquals(200,response.getStatus());
        JsonObject json = response.readEntity(JsonObject.class);
        json.keySet().stream().forEach(key -> System.out.println(key + ": " + json.get(key)));
        assertEquals(id, json.get("productCode").toString().replaceAll("\"", ""));
    }

    @Test
    public void testGetAll() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        Response response = web.request(MediaType.APPLICATION_JSON).get();
        JsonArray array = response.readEntity(JsonArray.class);
        array.stream().forEach(json -> {
            System.out.println(json);
            JsonObject jsonObject = json.asJsonObject();
            jsonObject.keySet().stream().forEach(key -> assertTrue("object with id " + jsonObject.getString(productCode) + " is corrupt", this.validate(jsonObject)));
            System.out.println();
        });
    }

    private boolean validate(JsonObject jsonObject) {
        return (
                !jsonObject.getString(productCode).isEmpty() &&
                        jsonObject.getString(productCode) != null &&
                        jsonObject.getString(productCode) != null &&
                        jsonObject.getString(productCode) != null
        );
    }
}
