package de.ostfalia.gruppe5.rest;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.internal.BasicAuthentication;
import org.junit.Before;
import org.junit.Test;

import javax.json.JsonObject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class ProductIT extends BasicIT<ProductProxy> {

    @Before
    public void init() {
        this.setProxyType(ProductProxy.class);
        this.setTestId("S10_1678");
        this.setPrimaryKey("productCode");
        this.setTestEntity("{\"productCode\":\"" + this.getPrimaryKeyToken() + "\"," +
                "\"productName\":\"" + this.getUpdateToken() + "\"," +
                "\"productLine\":" +
                "{\"productLine\":\"Ships\"," +
                "\"textDescription\":\"The perfect holiday or anniversary gift for executives, clients, friends, and family. These handcrafted model ships are unique, stunning works of art that will be treasured for generations! They come fully assembled and ready for display in the home or office. We guarantee the highest quality, and best value.\"," +
                "\"htmlDescription\":null," +
                "\"image\":null}," +
                "\"productScale\":\"1:72\"," +
                "\"productVendor\":\"Unimax Art Galleries\"," +
                "\"productDescription\":\"Measures 38 inches Long x 33 3/4 inches High. Includes a stand. Many extras including rigging, long boats, pilot house, anchors, etc. Comes with 2 masts, all square-rigged\"," +
                "\"quantityInStock\":200," +
                "\"buyPrice\":33.3," +
                "\"msrp\":54.6}");
        this.setUpdateKeyword("productName");
    }

    @Test
    public void testSepAuthGetSpecific() {
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget() + "/products/" + this.getTestId());
        Response response = web.request(MediaType.APPLICATION_JSON).header("SEP-Authorization", this.getUsername() + ":" + this.getPassword()).get();
        assertEquals(200, response.getStatus());
        JsonObject json = response.readEntity(JsonObject.class);
        assertEquals(this.getTestId(), json.get(this.getPrimaryKey()).toString().replaceAll("\"", ""));
        response.close();
    }

    @Test
    public void testGetAssignedProductLine() {
        String id = "";
//        Create Product
        String expectedLine = "{\"productLine\":\"Motorcycles\",\"textDescription\":\"Our motorcycles are state of the art replicas of classic as well as contemporary motorcycle legends such as Harley Davidson, Ducati and Vespa. Models contain stunning details such as official logos, rotating wheels, working kickstand, front suspension, gear-shift lever, footbrake lever, and drive chain. Materials used include diecast and plastic. The models range in size from 1:10 to 1:50 scale and include numerous limited edition and several out-of-production vehicles. All models come fully assembled and ready for display in the home or office. Most include a certificate of authenticity.\",\"htmlDescription\":null,\"image\":null}";
        ResteasyClient client = (ResteasyClient) ResteasyClientBuilder.newClient();
        ResteasyWebTarget web = client.target(this.getTarget());
        web.register(new BasicAuthentication(this.getUsername(), this.getPassword()));
        ProductProxy productProxy = web.proxy(ProductProxy.class);
        JsonObject line = productProxy.getProductAssignedProductLine(this.getTestId());
        assertEquals(expectedLine, line.toString());
    }

}
