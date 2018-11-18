package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.PaymentService;
import de.ostfalia.gruppe5.business.entity.Payment;

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
@Path("payments")
public class PaymentRessource {

    public PaymentRessource(){
    }

    @Inject
    private PaymentService service;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Payment> getPayments(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Payment getPayment(@PathParam("id") Integer id){
        return service.find(id);
    }

}
