package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ProductIT {

    private final String productCode = "productCode";
    private final String username = "Bow";
    private final String password = "1143";
    private final String s10_1678 = "S10_1678";
    private final String target = "http://localhost:8080/sep-gruppe-5/api/products/";

    @Test
    public void testSepAuthGetSpecific() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target + this.s10_1678);
        Response response = web.request(MediaType.APPLICATION_JSON).header("SEP-Authorization", username + ":" + password).get();
        assertEquals(200, response.getStatus());
        JsonObject json = response.readEntity(JsonObject.class);
        json.keySet().stream().forEach(key -> System.out.println(key + ": " + json.get(key)));
        assertEquals(this.s10_1678, json.get("productCode").toString().replaceAll("\"", ""));
        response.close();
    }

    @Test
    public void testGetSpecific() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        ProductProxy productProxy = web.proxy(ProductProxy.class);
        JsonObject json = productProxy.getProductById(this.s10_1678);
        json.keySet().stream().forEach(key -> System.out.println(key + ": " + json.get(key)));
        assertEquals(this.s10_1678, json.get("productCode").toString().replaceAll("\"", ""));
    }

    @Test
    public void testGetAll() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        ProductProxy productProxy = web.proxy(ProductProxy.class);
        JsonArray jsonArray = productProxy.getAllProducts();
        jsonArray.stream().forEach(json -> {
            System.out.println(json);
            JsonObject jsonObject = json.asJsonObject();
            jsonObject.keySet().stream().forEach(key -> assertTrue("object with s10_1678 " + jsonObject.getString(productCode) + " is corrupt", this.validate(jsonObject)));
            System.out.println();
        });
    }

    @Test
    public void testPost() {
        String id ="";
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        ProductProxy productProxy = web.proxy(ProductProxy.class);
        JsonArray arrayBefore = productProxy.getAllProducts();
        productProxy.createProduct(this.generateTestProduct(id));
        JsonArray arrayAfter = productProxy.getAllProducts();
        id = arrayAfter.get(arrayAfter.size()-1).asJsonObject().getString("productCode");
        assertNotEquals(arrayBefore.get(arrayBefore.size()-1).asJsonObject().getString("productCode"), id);
        System.out.println(id);
        productProxy.deleteProduct(id);
    }

    @Test
    public void testDeleteSpecificWrongID() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        ProductProxy productProxy = web.proxy(ProductProxy.class);
        Response response = productProxy.deleteProduct("NotARealIdOfAProduct");
//        System.out.println(response.readEntity(String.class));
        assertEquals(404, response.getStatus());
        response.close();
    }

    @Test
    public void testDeleteSpecific() {
        String id ="";
//        Create product
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        ProductProxy productProxy = web.proxy(ProductProxy.class);
        productProxy.createProduct(this.generateTestProduct(id));
        JsonArray array = productProxy.getAllProducts();
        JsonObject jsonForId = array.get(array.size() - 1).asJsonObject();
        id = jsonForId.getString("productCode");
        System.out.println("id is "+id);
        JsonObject productById = productProxy.getProductById(id);
        assertEquals(id, productById.get("productCode").toString().replaceAll("\"", ""));
//        Delete product
        Response response = productProxy.deleteProduct(id);
        assertEquals(200, response.getStatus());
//        System.out.println(response.readEntity(String.class));
        JsonObject json = response.readEntity(JsonObject.class);
        json.keySet().stream().forEach(key -> System.out.println(key + ": " + json.get(key)));
        assertEquals(id, json.get("productCode").toString().replaceAll("\"", ""));
        response.close();
    }

    @Test
    public void testPut() {
        String id ="";
//        Create Product
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        ProductProxy productProxy = web.proxy(ProductProxy.class);
        productProxy.createProduct(this.generateTestProduct(id,100));
//        JsonObject json = productProxy.getProductById(id);
        JsonArray array = productProxy.getAllProducts();
        JsonObject json = array.get(array.size() - 1).asJsonObject();
        id = json.getString("productCode");
        System.out.println("id is "+id);
        assertEquals(100, json.getInt("quantityInStock"));

//        Modify Product
        productProxy.putProduct(id,this.generateTestProduct(id,0));
        json = productProxy.getProductById(id);
        assertEquals(0, json.getInt("quantityInStock"));

//        Delete product
        Response response = productProxy.deleteProduct(id);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testGetAssignedProductLine(){
        String id ="";
//        Create Product
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        ProductProxy productProxy = web.proxy(ProductProxy.class);
        JsonArray array = productProxy.getAllProducts();
        JsonObject jsonForId = array.get(array.size() - 1).asJsonObject();
        id = jsonForId.getString("productCode");
        JsonObject productLineDirect = array.get(array.size() - 1).asJsonObject().getJsonObject("productLine");
        JsonObject productLineIndirect = productProxy.getProductById(id).getJsonObject("productLine");
        assertEquals(productLineDirect,productLineIndirect);
    }

    private boolean validate(JsonObject jsonObject) {
        return (
                !jsonObject.getString(productCode).isEmpty()
        );
    }

    public String generateTestProduct(String id){
        String testProduct = "{\"productCode\":\""+id+"\"," +
                "\"productName\":\"Pont Yacht\"," +
                "\"productLine\":" +
                "{\"productLine\":\"Ships\"," +
                "\"textDescription\":\"The perfect holiday or anniversary gift for executives, clients, friends, and family. These handcrafted model ships are unique, stunning works of art that will be treasured for generations! They come fully assembled and ready for display in the home or office. We guarantee the highest quality, and best value.\"," +
                "\"htmlDescription\":null," +
                "\"image\":null}," +
                "\"productScale\":\"1:72\"," +
                "\"productVendor\":\"Unimax Art Galleries\"," +
                "\"productDescription\":\"Measures 38 inches Long x 33 3/4 inches High. Includes a stand. Many extras including rigging, long boats, pilot house, anchors, etc. Comes with 2 masts, all square-rigged\"," +
                "\"quantityInStock\":414," +
                "\"buyPrice\":33.3," +
                "\"msrp\":54.6}";
        return testProduct;
    }

    public String generateTestProduct(String id, int quantityInStock){
        String testProduct = "{\"productCode\":\""+id+"\"," +
                "\"productName\":\"Pont Yacht\"," +
                "\"productLine\":" +
                "{\"productLine\":\"Ships\"," +
                "\"textDescription\":\"The perfect holiday or anniversary gift for executives, clients, friends, and family. These handcrafted model ships are unique, stunning works of art that will be treasured for generations! They come fully assembled and ready for display in the home or office. We guarantee the highest quality, and best value.\"," +
                "\"htmlDescription\":null," +
                "\"image\":null}," +
                "\"productScale\":\"1:72\"," +
                "\"productVendor\":\"Unimax Art Galleries\"," +
                "\"productDescription\":\"Measures 38 inches Long x 33 3/4 inches High. Includes a stand. Many extras including rigging, long boats, pilot house, anchors, etc. Comes with 2 masts, all square-rigged\"," +
                "\"quantityInStock\":"+quantityInStock+"," +
                "\"buyPrice\":33.3," +
                "\"msrp\":54.6}";
        return testProduct;
    }
}
