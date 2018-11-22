package de.ostfalia.gruppe5.rest;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public abstract class BasicIT<T extends BasicProxy, I> {

    private String primaryKey = "";
    private String username = "Bow";
    private String password = "1143";
    private I testId;
    private Class<T> proxyType;
    private Class<I> idType;
    private String target = "http://localhost:8080/sep-gruppe-5/api";
    private String testEntity = "";
    private String updateKeyword = "";
    private String primaryKeyToken = "%pk%";
    private String updateToken = "%update%";
    private I currentEntityID;
    private ResteasyClient client;
    private ResteasyWebTarget web;
    private T proxy;


    public void initEnvironment() {
        this.client = (ResteasyClient) ResteasyClientBuilder.newClient();
        this.web = this.client.target(this.target);
        this.web.register(new BasicAuthentication(this.username, this.password));
        this.proxy = this.web.proxy(this.proxyType);
        this.proxy.createEntity(this.generateTestEntity(this.testId, "100"));
        JsonArray array = this.proxy.getAllEntities();
        JsonObject jsonForId = array.get(array.size() - 1).asJsonObject();
        if (this.idType == (Integer.class)) {
            this.currentEntityID = (I) Integer.valueOf(jsonForId.get(this.primaryKey).toString());
        } else if (idType == (String.class)) {
            String idString = jsonForId.getString(this.primaryKey);
            if (idString.contains("\""))
                idString = idString.replaceAll("\"", "");
            this.currentEntityID = (I) idString;
        } else {
            assertTrue("Type of primaryKey is not registered, ask for help!!!", false);
        }


        if (idType==(Integer.class)) {

        } else if (idType==(String.class)) {

        }
    }

    public void cleanup() {
        System.out.println("++++++++ After BasicIT");
        Response response = null;

        if (idType == (Integer.class)) {
            response = this.proxy.deleteEntity((Integer) this.currentEntityID);
        } else if (idType == (String.class)) {
            response = this.proxy.deleteEntity((String) this.currentEntityID);
        }
        assertEquals(HttpStatus.SC_OK, response.getStatus());
        response.close();
    }

    @Test
    public void testGetSpecific() {
        this.initEnvironment();
        JsonObject json;
        if (idType==(Integer.class)) {
            json = proxy.getEntityById((Integer) this.testId);
            assertEquals(this.testId, json.getInt(primaryKey));
        } else if (idType==(String.class)) {
            json = proxy.getEntityById((String) this.testId);
            assertEquals(this.testId, json.getString(primaryKey));
        }
        this.cleanup();
    }

    @Test
    public void testGetAll() {
        this.initEnvironment();
        JsonArray jsonArray = proxy.getAllEntities();
        jsonArray.stream().forEach(json -> {
            JsonObject jsonObject = json.asJsonObject();
            jsonObject.keySet().stream().forEach(key -> assertTrue("object with PK " + jsonObject.get(primaryKey).toString() + " is corrupt", !jsonObject.get(primaryKey).toString().isEmpty()));
        });
        this.cleanup();
    }

//    @Test
//    public void testPost() {
//        String id = "";
//        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
//        ResteasyWebTarget web = client.target(target);
//        web.register(new BasicAuthentication(username, password));
//        T proxy = web.proxy(proxyType);
//        JsonArray arrayBefore = proxy.getAllEntities();
//        proxy.createEntity(this.generateTestEntity(this.testId));
//        JsonArray arrayAfter = proxy.getAllEntities();
//        id = arrayAfter.get(arrayAfter.size() - 1).asJsonObject().get(primaryKey).toString();
//        assertNotEquals(arrayBefore.get(arrayBefore.size() - 1).asJsonObject().get(primaryKey).toString(), id);
//        proxy.deleteEntity(id);
//    }

    @Test
    public void testDeleteSpecificWrongID() {
        this.initEnvironment();
        Response response = proxy.deleteEntity("NotARealIdOfAProduct");
//        System.out.println(response.readEntity(String.class));
        assertEquals(404, response.getStatus());
        response.close();
        this.cleanup();
    }

//    @Test
//    public void testDeleteSpecific() {
//        JsonObject productById = proxy.getEntityById(id);
//        assertEquals(id, productById.get(primaryKey).toString().replaceAll("\"", ""));
//    }

    @Test
    public void testPut() {
        this.initEnvironment();
//        Modify Product

        String modifiedEntity = this.generateTestEntity(this.currentEntityID, "0");
        JsonObject json = null;
        if (idType==(Integer.class)) {
            proxy.putEntity((Integer) this.currentEntityID, modifiedEntity);
            json = proxy.getEntityById((Integer) this.currentEntityID);
        } else if (idType==(String.class)) {
            proxy.putEntity((String) this.currentEntityID, modifiedEntity);
            json = proxy.getEntityById((String) this.currentEntityID);
        }
        System.out.println(json);

        String updateValue = json.get(updateKeyword).toString();
        System.out.println("updateValue: " + updateValue);
        String quotationToken = "\"";
        if (updateValue.contains(quotationToken))
            updateValue = updateValue.replaceAll(quotationToken, "");
        System.out.println("updateValue: " + updateValue);
        assertEquals("0", updateValue);
        this.cleanup();
    }

    public String generateTestEntity(I id, String updateValue) {
        System.out.println("++++++++++++ Generate id+update");
        String newEntity = this.testEntity;
        if (idType==(Integer.class)) {
            newEntity = newEntity.replaceAll(primaryKeyToken, id.toString());
        } else if (idType==(String.class)) {
            newEntity = newEntity.replaceAll(primaryKeyToken, id.toString());
        }

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

    public I getTestId() {
        return testId;
    }

    public Class<T> getProxyType() {
        return proxyType;
    }

    public void setProxyType(Class<T> proxyType) {
        System.out.println("+++++++++++ setProxyType to " + proxyType.getSimpleName());
        this.proxyType = proxyType;
    }

    public void setTestId(I testId) {
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

    public Class<I> getIdType() {
        return idType;
    }

    public void setIdType(Class<I> idType) {
        this.idType = idType;
    }
}
