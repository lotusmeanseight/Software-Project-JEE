package de.ostfalia.gruppe5.rest;

import javax.json.JsonArray;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/orders")
public interface OrderProxy extends BasicProxy {
    @GET
    @Path("/{id}/assignedCustomer")
    @Produces(MediaType.APPLICATION_JSON)
    Integer getAssignedCustomer(@PathParam("id") Integer id);

    @GET
    @Path("/{id}/orderDetails")
    @Produces(MediaType.APPLICATION_JSON)
    JsonArray getOrderDetails(@PathParam("id") Integer id);
}