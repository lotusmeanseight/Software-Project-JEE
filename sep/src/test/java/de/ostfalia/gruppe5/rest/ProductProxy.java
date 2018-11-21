package de.ostfalia.gruppe5.rest;

import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/products")
public interface ProductProxy extends BasicProxy {
    @GET
    @Path("/{id}/assignedproductLine")
    @Produces(MediaType.APPLICATION_JSON)
    ProductLine getProductAssignedProductLine(@PathParam("id") String id);
}