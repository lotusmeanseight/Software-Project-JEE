package de.ostfalia.gruppe5.rest;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public interface ProductProxy {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getProductById(@PathParam("id") String id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    JsonArray getAllProducts();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void createProduct(String productJson);

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response deleteProduct(@PathParam("id") String id);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response putProduct(@PathParam("id") String id,String productJson);
}
