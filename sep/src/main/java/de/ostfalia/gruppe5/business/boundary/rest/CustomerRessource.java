package de.ostfalia.gruppe5.business.boundary.rest;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.ostfalia.gruppe5.business.boundary.CustomerService;
import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.Employee;

@RolesAllowed("EMPLOYEE")
@Stateless
@Path("customers")
public class CustomerRessource {

	@Inject
	private CustomerService service;

	@Context
	private UriInfo uriinfo;

	public CustomerRessource() {
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		return "Hello World!";
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getCustomers() {
		return service.findAll();
	}

	@GET
	@Path("/{id}")
	public Customer getCustomer(@PathParam("id") Integer id) {
		return service.find(id);
	}

	@GET
	@Path("/{id}/assignedEmployee")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAssignedEmployee(@PathParam("id") Integer id) {

		Customer customer = service.find(id);
		if (customer == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		Employee employee = customer.getSalesRepEmployeeNumber();
		if (employee == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok(employee).build();
		}

	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCustomer(Customer customer) {

		service.save(customer);
		Customer c = service.find(customer.getCustomerNumber());
		UriBuilder builder = uriinfo.getRequestUriBuilder();
		URI uri = builder.path(CustomerRessource.class, "getCustomer").build(c.getCustomerNumber());
		return Response.created(uri).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateCustomer(@PathParam("id") Integer id, Customer customer) {
		if (id != customer.getCustomerNumber()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Customer c = service.update(customer);
		if (c == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok().build();
		}

	}

	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteCustomer(@PathParam("id") Integer id) {
		Customer customer = service.find(id);
		if (customer == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			service.delete(customer);
			return Response.ok().build();
		}
	}

}
