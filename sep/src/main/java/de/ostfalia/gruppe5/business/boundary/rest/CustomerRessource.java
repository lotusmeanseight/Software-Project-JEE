package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.CustomerService;
import de.ostfalia.gruppe5.business.entity.Customer;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
@Path("customers")
public class CustomerRessource {

    @Inject
    private CustomerService service;

    public CustomerRessource(){
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> getCustomers(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Customer getCustomer(@PathParam("id") Integer id){
        return service.find(id);
    }

}
