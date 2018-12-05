package de.ostfalia.gruppe5.rest;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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

    @POST
    @Path("/basket")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    Response postOrderBasket(JsonObject json);

    @POST
    @Path("/basket/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    Response postOrderBasket(@PathParam("id") Integer id, JsonObject json);
}