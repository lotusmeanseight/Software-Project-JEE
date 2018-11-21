package JAXB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;

import de.ostfalia.gruppe5.business.entity.Customer;

public class CustomerRestTest {

	private static final Logger LOGGER = Logger.getLogger(CustomerRestTest.class.getName());

	private static final String AuthorizationHeader = "SEP-Authorization";
	private static final String BaseUrl = "http://localhost:8080/sep-gruppe-5/api/customers";

	private MultivaluedMap<String, Object> headers;

	@Before
	public void init() {
		headers = new MultivaluedHashMap<>();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON);
		headers.add(AuthorizationHeader, "Murphy:1002");
	}

	public Customer createCustomer() {
		Customer customer = new Customer();
		customer.setCustomerNumber(1234);
		customer.setAddressLine1("gag");
		customer.setAddressLine2("haha");
		customer.setContactFirstName("asd");
		customer.setContactLastName(" haha");
		customer.setCustomerName("peter");
		customer.setPhone("123");
		customer.setCity("bas");
		customer.setState("sadas");
		customer.setPostalCode("123");
		customer.setCountry("haha");
		customer.setSalesRepEmployeeNumber(null);

		return customer;

	}

	private Response post(Customer customer) {
		Response response = null;

		Client client = ClientBuilder.newClient();
		try {
			// client.register(Customer.class);

			WebTarget target = client.target(BaseUrl);
			Builder builder = target.request().headers(headers).header(HttpHeaders.ACCEPT, MediaType.TEXT_PLAIN);
			Entity<Customer> entity = Entity.entity(customer, MediaType.APPLICATION_JSON);

			response = builder.post(entity);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			fail();
		} finally {
			client.close();
		}

		return response;
	}

	@Test
	public void testPost() {
		Customer c = createCustomer();

		Response r = post(c);

		assertEquals(Status.OK.getStatusCode(), r.getStatus());
		assertNotNull(r.getHeaderString(HttpHeaders.LOCATION));

	}
}
