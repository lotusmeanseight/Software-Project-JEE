package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Before;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrderEmployeeIT extends BasicIT<OrderProxy, Integer,String> {
    @Before
    public void init() {
        this.setProxyType(OrderProxy.class);
        this.setTestId(10100);
        this.setPrimaryKey("orderNumber");
        this.setTestEntity("{\"orderNumber\":"+this.getPrimaryKeyToken()+",\"orderDate\":{\"year\":2003,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"era\":\"CE\",\"dayOfYear\":6,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":6,\"monthValue\":1},\"requiredDate\":{\"year\":2003,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"era\":\"CE\",\"dayOfYear\":13,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":13,\"monthValue\":1},\"shippedDate\":{\"year\":2003,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"era\":\"CE\",\"dayOfYear\":10,\"dayOfWeek\":\"FRIDAY\",\"leapYear\":false,\"dayOfMonth\":10,\"monthValue\":1},\"status\":\"Shipped\",\"comments\":"+this.getUpdateToken()+",\"customerNumber\":363}");
        this.setUpdateKeyword("comments");
        this.setIdType(Integer.class);
        this.setUpdateType(String.class);
        this.setUpdateBase("100");
        this.setUpdateGoal("0");
    }

    @Test
    public void testGetAssignedCustomer() {
        Integer id = this.getTestId();
        Integer expected = 363;
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        OrderProxy orderProxy = web.proxy(OrderProxy.class);
        Integer customer = orderProxy.getAssignedCustomer(id);
        assertEquals(expected, customer);
    }

    @Test
    public void testGetOrderDetails() {
        Integer id = this.getTestId();
        String[] expected = new String[]
                {"{\"orderNumber\":{\"orderNumber\":10100,\"orderDate\":{\"year\":2003,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"era\":\"CE\",\"dayOfYear\":6,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":6,\"monthValue\":1},\"requiredDate\":{\"year\":2003,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"era\":\"CE\",\"dayOfYear\":13,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":13,\"monthValue\":1},\"shippedDate\":{\"year\":2003,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"},\"era\":\"CE\",\"dayOfYear\":10,\"dayOfWeek\":\"FRIDAY\",\"leapYear\":false,\"dayOfMonth\":10,\"monthValue\":1},\"status\":\"Shipped\",\"comments\":null,\"customerNumber\":363},\"productCode\":{\"productCode\":\"S18_1749\",\"productName\":\"1917 Grand Touring Sedan\",\"productLine\":{\"productLine\":\"Vintage Cars\",\"textDescription\":\"Our Vintage Car models realistically portray automobiles produced from the early 1900s through the 1940s. Materials used include Bakelite, diecast, plastic and wood. Most of the replicas are in the 1:18 and 1:24 scale sizes, which provide the optimum in detail and accuracy. Prices range from $30.00 up to $180.00 for some special limited edition replicas. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.\",\"htmlDescription\":null,\"image\":null},\"productScale\":\"1:18\",\"productVendor\":\"Welly Diecast Productions\",\"productDescription\":\"This 1:18 scale replica of the 1917 Grand Touring car has all the features you would expect from museum quality reproductions: all four doors and bi-fold hood opening, detailed engine and instrument panel, chrome-look trim, and tufted upholstery, all topped off with a factory baked-enamel finish.\",\"quantityInStock\":2724,\"buyPrice\":86.7,\"msrp\":170.0},\"quantityOrdered\":30,\"priceEach\":136.0,\"orderLineNumber\":3}"};
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        OrderProxy orderProxy = web.proxy(OrderProxy.class);
        JsonArray orderDetails = orderProxy.getOrderDetails(id);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i],orderDetails.get(i).toString());
        }
    }

    @Test
    public void testPostOrderBasket() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        OrderProxy orderProxy = web.proxy(OrderProxy.class);
        String jsonString = "{\"iban\":\"DE08700901001234567890\",\"kntnr\":\"\",\"blz\":\"\",\"orders\":[{\"productCode\":\"S12_1099\",\"amount\":3}]}";
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        Response response = orderProxy.postOrderBasket(103,jsonObject);
        assertTrue("Location can not be read", response.getLocation() != null);
        assertEquals(201, response.getStatus());
    }
}
