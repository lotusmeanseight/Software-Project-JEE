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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.*;

public abstract class BasicIT<T extends BasicProxy, I, U> {

    private I currentEntityID;
    private I testId;
    private U updateBase;
    private U updateGoal;
    private T proxy;
    private Class<T> proxyType;
    private Class<I> idType;
    private Class<U> updateType;
    private String primaryKey = "";
    private String username = "Bow";
    private String password = "1143";
    private String target = "http://localhost:8080/sep-gruppe-5/api";
    private String testEntity = "";
    private String updateKeyword = "";
    private String primaryKeyToken = "%pk%";
    private String updateToken = "%update%";
    private ResteasyClient client;
    private ResteasyWebTarget web;


    public void initEnvironment() {
        this.client = (ResteasyClient) ResteasyClientBuilder.newClient();
        this.web = this.client.target(this.target);
        this.web.register(new BasicAuthentication(this.username, this.password));
        this.proxy = this.web.proxy(this.proxyType);
        this.proxy.createEntity(this.generateTestEntity(this.testId, updateBase));
        JsonArray array = this.proxy.getAllEntities();
        JsonObject jsonForId = array.get(array.size() - 1).asJsonObject();
        U generatedValue = null;
        if (this.idType == (Integer.class)) {
            this.currentEntityID = (I) Integer.valueOf(jsonForId.get(this.primaryKey).toString());
            generatedValue = (U) jsonForId.get(this.updateKeyword).toString().replaceAll("(\\\\)*\"","");
        } else if (idType == (String.class)) {
            String idString = jsonForId.get(this.primaryKey).toString();
            if (idString.contains("\""))
                idString = idString.replaceAll("\"", "");
            this.currentEntityID = (I) idString;

            generatedValue = (U) jsonForId.get(this.updateKeyword).toString().replaceAll("(\\\\)*\"","");
        } else {
            assertTrue("Type of primaryKey is not registered, ask for help!!!", false);
        }

        if (this.updateType == (Integer.class)) {
            assertEquals(updateBase, generatedValue);
        } else if (this.updateType == (String.class)) {
            assertEquals(updateBase, generatedValue);
        } else if (this.updateType == (BigDecimal.class)) {
            DecimalFormat df = new DecimalFormat();
            df.setParseBigDecimal(true);
            BigDecimal bd = new BigDecimal(0);
            try {
                bd = (BigDecimal) df.parse(generatedValue.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assertEquals(updateBase, bd);
        }else{
            assertTrue("Type of updateValue is not registered, ask for help!!!", false);
        }
    }

    public void cleanup() {
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
        if (idType == (Integer.class)) {
            json = proxy.getEntityById((Integer) this.testId);
            assertEquals(this.testId, json.getInt(primaryKey));
        } else if (idType == (String.class)) {
            json = proxy.getEntityById((String) this.testId);
            assertEquals(this.testId, json.getString(primaryKey).replaceAll("\"",""));
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

    @Test
    public void testDeleteSpecificWrongID() {
        this.initEnvironment();
        Response response = proxy.deleteEntity("NotARealIdOfAProduct");
        assertEquals(404, response.getStatus());
        response.close();
        this.cleanup();
    }

    @Test
    public void testPut() {
        this.initEnvironment();

        String modifiedEntity = this.generateTestEntity(this.currentEntityID, updateGoal);
        JsonObject json = null;
        if (idType == (Integer.class)) {
            proxy.putEntity((Integer) this.currentEntityID, modifiedEntity);
            json = proxy.getEntityById((Integer) this.currentEntityID);
        } else if (idType == (String.class)) {
            proxy.putEntity((String) this.currentEntityID, modifiedEntity);
            json = proxy.getEntityById((String) this.currentEntityID);
        }
        U updateValue = null;
        if (this.updateType == (Integer.class)) {
            updateValue = (U) String.valueOf(json.get(updateKeyword)).replaceAll("\"","");
            assertEquals(updateGoal, updateValue);
        } else if (this.updateType == (String.class)) {
            updateValue = (U) json.getString(updateKeyword).replaceAll("\"","");
            assertEquals(updateGoal, updateValue);
        } else if (this.updateType == (BigDecimal.class)) {
            DecimalFormat df = new DecimalFormat();
            df.setParseBigDecimal(true);
            df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.getDefault()));
            BigDecimal bd = new BigDecimal(0);
            try {
                bd = (BigDecimal) df.parse(json.get(updateKeyword).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assertEquals(updateGoal, bd);
        }
        this.cleanup();
    }

    public String generateTestEntity(I id, U updateValue) {
        String newEntity = this.testEntity;

        if (idType == (Integer.class)) {
            newEntity = newEntity.replaceAll(primaryKeyToken, String.valueOf(id));
        } else if (idType == (String.class)) {
            newEntity = newEntity.replaceAll(primaryKeyToken, "\""+(String)id+"\"");
        }

        if (this.updateType == (Integer.class)) {
            newEntity = newEntity.replaceAll(updateToken, ((Integer)updateValue).toString());
        } else if (this.updateType == (String.class)) {
            newEntity = newEntity.replaceAll(updateToken, "\""+updateValue+"\"");
        } else if (this.updateType == (BigDecimal.class)) {
            BigDecimal updateValue1 = (BigDecimal) updateValue;
            String bd = updateValue1.toString();
            newEntity = newEntity.replaceAll(updateToken, bd);
        }
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

    public U getUpdateBase() {
        return updateBase;
    }

    public void setUpdateBase(U updateBase) {
        this.updateBase = updateBase;
    }

    public U getUpdateGoal() {
        return updateGoal;
    }

    public void setUpdateGoal(U updateGoal) {
        this.updateGoal = updateGoal;
    }

    public Class<U> getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Class<U> updateType) {
        this.updateType = updateType;
    }
}
