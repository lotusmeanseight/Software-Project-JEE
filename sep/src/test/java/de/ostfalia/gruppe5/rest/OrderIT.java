package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonArray;

import static org.junit.Assert.assertEquals;

public class OrderIT extends BasicIT<OrderProxy, Integer,String> {
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
                {"{\"id\":{\"orderNumber\":10100,\"productCode\":\"S18_1749\"},\"orderNumber\":{\"orderNumber\":10100,\"orderDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":6,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":6,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"requiredDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":13,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":13,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"shippedDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":10,\"dayOfWeek\":\"FRIDAY\",\"leapYear\":false,\"dayOfMonth\":10,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"status\":\"Shipped\",\"comments\":null,\"customerNumber\":363},\"productCode\":{\"productCode\":\"S18_1749\",\"productName\":\"1917 Grand Touring Sedan\",\"productLine\":{\"productLine\":\"Vintage Cars\",\"textDescription\":\"Our Vintage Car models realistically portray automobiles produced from the early 1900s through the 1940s. Materials used include Bakelite, diecast, plastic and wood. Most of the replicas are in the 1:18 and 1:24 scale sizes, which provide the optimum in detail and accuracy. Prices range from $30.00 up to $180.00 for some special limited edition replicas. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.\",\"htmlDescription\":null,\"image\":null},\"productScale\":\"1:18\",\"productVendor\":\"Welly Diecast Productions\",\"productDescription\":\"This 1:18 scale replica of the 1917 Grand Touring car has all the features you would expect from museum quality reproductions: all four doors and bi-fold hood opening, detailed engine and instrument panel, chrome-look trim, and tufted upholstery, all topped off with a factory baked-enamel finish.\",\"quantityInStock\":2724,\"buyPrice\":86.7,\"msrp\":170.0},\"quantityOrdered\":30,\"priceEach\":136.0,\"orderLineNumber\":3}",
                "{\"id\":{\"orderNumber\":10100,\"productCode\":\"S18_2248\"},\"orderNumber\":{\"orderNumber\":10100,\"orderDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":6,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":6,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"requiredDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":13,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":13,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"shippedDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":10,\"dayOfWeek\":\"FRIDAY\",\"leapYear\":false,\"dayOfMonth\":10,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"status\":\"Shipped\",\"comments\":null,\"customerNumber\":363},\"productCode\":{\"productCode\":\"S18_2248\",\"productName\":\"1911 Ford Town Car\",\"productLine\":{\"productLine\":\"Vintage Cars\",\"textDescription\":\"Our Vintage Car models realistically portray automobiles produced from the early 1900s through the 1940s. Materials used include Bakelite, diecast, plastic and wood. Most of the replicas are in the 1:18 and 1:24 scale sizes, which provide the optimum in detail and accuracy. Prices range from $30.00 up to $180.00 for some special limited edition replicas. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.\",\"htmlDescription\":null,\"image\":null},\"productScale\":\"1:18\",\"productVendor\":\"Motor City Art Classics\",\"productDescription\":\"Features opening hood, opening doors, opening trunk, wide white wall tires, front door arm rests, working steering system.\",\"quantityInStock\":540,\"buyPrice\":33.3,\"msrp\":60.54},\"quantityOrdered\":50,\"priceEach\":55.09,\"orderLineNumber\":2}",
                "{\"id\":{\"orderNumber\":10100,\"productCode\":\"S18_4409\"},\"orderNumber\":{\"orderNumber\":10100,\"orderDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":6,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":6,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"requiredDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":13,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":13,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"shippedDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":10,\"dayOfWeek\":\"FRIDAY\",\"leapYear\":false,\"dayOfMonth\":10,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"status\":\"Shipped\",\"comments\":null,\"customerNumber\":363},\"productCode\":{\"productCode\":\"S18_4409\",\"productName\":\"1932 Alfa Romeo 8C2300 Spider Sport\",\"productLine\":{\"productLine\":\"Vintage Cars\",\"textDescription\":\"Our Vintage Car models realistically portray automobiles produced from the early 1900s through the 1940s. Materials used include Bakelite, diecast, plastic and wood. Most of the replicas are in the 1:18 and 1:24 scale sizes, which provide the optimum in detail and accuracy. Prices range from $30.00 up to $180.00 for some special limited edition replicas. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.\",\"htmlDescription\":null,\"image\":null},\"productScale\":\"1:18\",\"productVendor\":\"Exoto Designs\",\"productDescription\":\"This 1:18 scale precision die cast replica features the 6 front headlights of the original, plus a detailed version of the 142 horsepower straight 8 engine, dual spares and their famous comprehensive dashboard. Color black.\",\"quantityInStock\":6553,\"buyPrice\":43.26,\"msrp\":92.03},\"quantityOrdered\":22,\"priceEach\":75.46,\"orderLineNumber\":4}",
                "{\"id\":{\"orderNumber\":10100,\"productCode\":\"S24_3969\"},\"orderNumber\":{\"orderNumber\":10100,\"orderDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":6,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":6,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"requiredDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":13,\"dayOfWeek\":\"MONDAY\",\"leapYear\":false,\"dayOfMonth\":13,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"shippedDate\":{\"year\":2003,\"month\":\"JANUARY\",\"era\":\"CE\",\"dayOfYear\":10,\"dayOfWeek\":\"FRIDAY\",\"leapYear\":false,\"dayOfMonth\":10,\"monthValue\":1,\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"status\":\"Shipped\",\"comments\":null,\"customerNumber\":363},\"productCode\":{\"productCode\":\"S24_3969\",\"productName\":\"1936 Mercedes Benz 500k Roadster\",\"productLine\":{\"productLine\":\"Vintage Cars\",\"textDescription\":\"Our Vintage Car models realistically portray automobiles produced from the early 1900s through the 1940s. Materials used include Bakelite, diecast, plastic and wood. Most of the replicas are in the 1:18 and 1:24 scale sizes, which provide the optimum in detail and accuracy. Prices range from $30.00 up to $180.00 for some special limited edition replicas. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.\",\"htmlDescription\":null,\"image\":null},\"productScale\":\"1:24\",\"productVendor\":\"Red Start Diecast\",\"productDescription\":\"This model features grille-mounted chrome horn, lift-up louvered hood, fold-down rumble seat, working steering system and rubber wheels. Color black.\",\"quantityInStock\":2081,\"buyPrice\":21.75,\"msrp\":41.03},\"quantityOrdered\":49,\"priceEach\":35.29,\"orderLineNumber\":1}"};
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        OrderProxy orderProxy = web.proxy(OrderProxy.class);
        JsonArray orderDetails = orderProxy.getOrderDetails(id);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i],orderDetails.get(i).toString());
        }
    }
}
