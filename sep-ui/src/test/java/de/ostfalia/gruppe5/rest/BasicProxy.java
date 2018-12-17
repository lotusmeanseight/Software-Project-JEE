package de.ostfalia.gruppe5.rest;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract interface BasicProxy {
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getEntityById(@PathParam("id") String id);

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getEntityById(@PathParam("id") Integer id);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    JsonArray getAllEntities();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    void createEntity(String productJson);

    @DELETE
    @Path("/{id}")
    Response deleteEntity(@PathParam("id") String id);

    @DELETE
    @Path("/{id}")
    Response deleteEntity(@PathParam("id") Integer id);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response putEntity(@PathParam("id") Integer id, String productJson);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response putEntity(@PathParam("id") String id, String productJson);
}
