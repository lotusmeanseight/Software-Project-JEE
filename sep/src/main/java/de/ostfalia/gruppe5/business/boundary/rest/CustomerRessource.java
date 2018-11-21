package de.ostfalia.gruppe5.business.boundary.rest;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import de.ostfalia.gruppe5.business.boundary.CustomerService;
import de.ostfalia.gruppe5.business.boundary.EmployeeService;
import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.Employee;
import netscape.javascript.JSObject;

@RolesAllowed("EMPLOYEE")
@Stateless
@Path("/customers")
public class CustomerRessource {

	@Inject
	private CustomerService customerService;

	@Inject
	private EmployeeService employeeService;

	@Context
	private UriInfo uriinfo;

	public CustomerRessource() {
	}


	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getCustomers() {
		return customerService.findAll();
	}

	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("id") Integer id) {
		return customerService.find(id);
	}

	@POST
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postCustomer(JsonObject jsonObject) {
		Customer customer = new Customer();
		populateCustomer(jsonObject, customer);
		customerService.save(customer);
		Customer c = customerService.find(customer.getCustomerNumber());
		UriBuilder builder = uriinfo.getRequestUriBuilder();
		URI uri = builder.path(CustomerRessource.class, "getCustomer").build(c.getCustomerNumber());
		return Response.created(uri).build();
	}

	private void populateCustomer(JsonObject jsonObject, Customer customer){
		customer.setState(jsonObject.getString("state"));
		customer.setCreditLimit(Double.parseDouble(jsonObject.getString("creditLimit")));
		customer.setPostalCode(jsonObject.getString("postalcode"));
		customer.setCountry(jsonObject.getString("country"));
		customer.setContactLastName(jsonObject.getString("contactLastName"));
		customer.setContactFirstName(jsonObject.getString("contactFirstName"));
		customer.setAddressLine1(jsonObject.getString("addressLine1"));
		customer.setAddressLine2(jsonObject.getString("addressLine2"));
		customer.setCity(jsonObject.getString("city"));
		customer.setPhone(jsonObject.getString("phone"));

		JsonObject employee = jsonObject.getJsonObject("salesRepEmployeeNumber");
		Integer employeeID  = employee.getInt("employeeNumber");
		Employee employeeObject = employeeService.find(employeeID);
		customer.setSalesRepEmployeeNumber(employeeObject);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putCustomer(@PathParam("id") Integer id, JsonObject jsonObject) {
		Customer customer = customerService.find(id);
		Integer jsonId = jsonObject.getInt("customerNumber");
		if(!customer.getCustomerNumber().equals(jsonId)) {
			return Response.status(400).build();
		}else{
			populateCustomer(jsonObject, customer);
			customerService.update(customer);
			GenericEntity<Customer> genericEntity = new GenericEntity<>(customerService.find(id)
			, Customer.class);
			return Response.ok().entity(genericEntity).build();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCustomer(@PathParam("id") Integer id) {
		Customer customer = customerService.find(id);
		if(customer == null){
			return Response.status(404).build();
		}
		GenericEntity<Customer> entity = new GenericEntity<>(customer, Customer.class);
		customerService.deleteById(id);
		return Response.ok().entity(entity).build();
	}

	@GET
	@Path("/{id}/assignedEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	public Employee getAssignedEmployee(@PathParam("id") Integer id) {
		return customerService.find(id).getSalesRepEmployeeNumber();
	}

}
