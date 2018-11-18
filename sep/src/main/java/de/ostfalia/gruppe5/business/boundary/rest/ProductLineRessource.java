package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.ProductLineService;
import de.ostfalia.gruppe5.business.entity.ProductLine;

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
@Path("productlines")
public class ProductLineRessource {

    public ProductLineRessource(){
    }

    @Inject
    private ProductLineService service;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductLine> getProductLines(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductLine getProductLine(@PathParam("id") String id){
        return service.find(id);
    }
}
