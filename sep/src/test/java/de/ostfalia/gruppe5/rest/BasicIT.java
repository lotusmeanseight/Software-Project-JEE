package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public abstract class BasicIT<T extends BasicProxy> {

    private String primaryKey = "";
    private String username = "Bow";
    private String password = "1143";
    private String testId = "";
    private Class<T> proxyType;
    private String target = "http://localhost:8080/sep-gruppe-5/api";
    private String testEntity = "";
    private String updateKeyword = "";
    private String primaryKeyToken = "%pk%";
    private String updateToken = "%update%";

    @Test
    public void testGetSpecific() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        T proxy = web.proxy(proxyType);
        JsonObject json = proxy.getEntityById(this.testId);
        System.out.println(json);
//        json.keySet().stream().forEach(key -> System.out.println(key + ": " + json.get(key)));
        assertEquals(this.testId, json.get(primaryKey).toString().replaceAll("\"", ""));
    }

    @Test
    public void testGetAll() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        T proxy = web.proxy(proxyType);
        JsonArray jsonArray = proxy.getAllEntities();
        jsonArray.stream().forEach(json -> {
            System.out.println(json);
            JsonObject jsonObject = json.asJsonObject();
            jsonObject.keySet().stream().forEach(key -> assertTrue("object with PK " + jsonObject.get(primaryKey).toString() + " is corrupt", !jsonObject.get(primaryKey).toString().isEmpty()));
            System.out.println();
        });
    }

    @Test
    public void testPost() {
        String id = "";
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        T proxy = web.proxy(proxyType);
        JsonArray arrayBefore = proxy.getAllEntities();
        proxy.createEntity(this.generateTestEntity(this.testId));
        JsonArray arrayAfter = proxy.getAllEntities();
        id = arrayAfter.get(arrayAfter.size() - 1).asJsonObject().get(primaryKey).toString();
        assertNotEquals(arrayBefore.get(arrayBefore.size() - 1).asJsonObject().get(primaryKey).toString(), id);
        proxy.deleteEntity(id);
    }

    @Test
    public void testDeleteSpecificWrongID() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        T proxy = web.proxy(proxyType);
        Response response = proxy.deleteEntity("NotARealIdOfAProduct");
//        System.out.println(response.readEntity(String.class));
        assertEquals(404, response.getStatus());
        response.close();
    }

    @Test
    public void testDeleteSpecific() {
        String id = "";
//        Create product
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        T proxy = web.proxy(proxyType);
        proxy.createEntity(this.generateTestEntity(this.testId));
        JsonArray array = proxy.getAllEntities();
        JsonObject jsonForId = array.get(array.size() - 1).asJsonObject();
        id = jsonForId.get(primaryKey).toString().replaceAll("\"", "");
        JsonObject productById = proxy.getEntityById(id);
        System.out.println(productById.get(primaryKey).toString().replaceAll("\"", ""));
        assertEquals(id, productById.get(primaryKey).toString().replaceAll("\"", ""));
//        Delete product
        Response response = proxy.deleteEntity(id);
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testPut() {
        String id = "123";
//        Create Product
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(target);
        web.register(new BasicAuthentication(username, password));
        T proxy = web.proxy(proxyType);
        proxy.createEntity(this.generateTestEntity(id, "100"));
        JsonArray array = proxy.getAllEntities();
        JsonObject json = array.get(array.size() - 1).asJsonObject();
        id = json.get(primaryKey).toString();
        if (id.contains("\""))
            id = id.replaceAll("\"", "");
        System.out.println("id is " + id);
        String updateValue = json.get(updateKeyword).toString();
        if (updateValue.contains("\""))
            updateValue = updateValue.replaceAll("\"", "");
        assertEquals("100", updateValue);

//        Modify Product
        String modifiedEntity = this.generateTestEntity(id, "0");
        System.out.println("+++++++++");
        System.out.println(modifiedEntity);
        proxy.putEntity(id, modifiedEntity);
        json = proxy.getEntityById(id);
        System.out.println(json);
        System.out.println("+++++++++");
        updateValue = json.get(updateKeyword).toString();
        if (updateValue.contains("\""))
            updateValue = updateValue.replaceAll("\"", "");
        assertEquals("0", updateValue);

//        Delete product
        Response response = proxy.deleteEntity(id);
        assertEquals(200, response.getStatus());
    }

    public String generateTestEntity(String id) {
        String newEntity = this.testEntity.replaceAll(primaryKeyToken, id);
        newEntity = newEntity.replaceAll(updateToken, "NA");
        return newEntity;
    }

    public String generateTestEntity(String id, String updateValue) {
        String newEntity = this.testEntity;
        newEntity = newEntity.replaceAll(primaryKeyToken, id);
        newEntity = newEntity.replaceAll(updateToken, updateValue);
        return newEntity;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTarget() {
        return target;
    }

    public String getTestId() {
        return testId;
    }

    public Class<T> getProxyType() {
        return proxyType;
    }

    public void setProxyType(Class<T> proxyType) {
        this.proxyType = proxyType;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestEntity() {
        return testEntity;
    }

    public void setTestEntity(String testEntity) {
        this.testEntity = testEntity;
    }

    public String getUpdateKeyword() {
        return updateKeyword;
    }

    public void setUpdateKeyword(String updateKeyword) {
        this.updateKeyword = updateKeyword;
    }

    public String getPrimaryKeyToken() {
        return primaryKeyToken;
    }

    public String getUpdateToken() {
        return updateToken;
    }


}
