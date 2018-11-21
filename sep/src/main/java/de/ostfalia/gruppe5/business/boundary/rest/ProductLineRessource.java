package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.ProductLineService;
import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
@Path("/productlines")
public class ProductLineRessource {

    @Inject
    private ProductLineService productLineService;
    @Context
    private UriInfo uriinfo;
    @PersistenceContext
    private EntityManager entityManager;
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductLine> getProductLines() {
        return productLineService.findAll();
    }
    
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductLine getProductLine(@PathParam("id") String id) {
        return productLineService.find(id);
    }
    
    @POST
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProductLine(JsonObject json) {
        System.out.println("############################################### POST");
        System.out.println(json);
        System.out.println("############# Product");
        ProductLine productLine = new ProductLine();
        productLine.setProductLine(productLineService.nextID());
        populateProductLine(json, productLine);
        System.out.println("############# ProductLine");
        productLineService.save(productLine);
        System.out.println("############# Response");
        ProductLine parsed = productLineService.find(productLine.getProductLine());
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(ProductRessource.class, "getProduct").build(parsed.getProductLine());
        return Response.created(uri).build();
    }
    
    private void populateProductLine(JsonObject json, ProductLine productLine) {
    	productLine.setHtmlDescription(json.getString("htmlDescription"));
    	productLine.setTextDescription(json.getString("textDescription"));
    	productLine.setImage(json.get("image").toString().getBytes());
    }
    
    @PUT
    @Path("/{id}")
    public Response putProductLine(@PathParam("id") String id, JsonObject json) {
        System.out.println("############################################### PUT");
        ProductLine productLine = productLineService.find(id);
        String jsonId = json.getString("productLine");
        if (!productLine.getProductLine().equals(jsonId)) {
            System.out.println("################ "+id+" not same as "+jsonId);
            return Response.status(400).build();
        }
        populateProductLine(json,productLine);
        System.out.println("+++++++++++++++++");
        System.out.println(productLine.getProductLine());
        System.out.println(productLine.getHtmlDescription());
        System.out.println(productLine.getImage());
        System.out.println(productLine.getTextDescription());
        System.out.println("+++++++++++++++++");
        productLineService.update(productLine);

        GenericEntity<ProductLine> entity = new GenericEntity<>(productLineService.find(id), ProductLine.class);
        return Response.ok().entity(entity).build();
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteProductLine(@PathParam("id") String id) {
        System.out.println("############################################### DELETE");
        ProductLine productLine = productLineService.find(id);
        if (productLine == null) {
            return Response.status(404).build();
        }
        GenericEntity<ProductLine> entity = new GenericEntity<>(productLine, ProductLine.class);
        //TODO detached entity
        productLineService.deleteById(id);
        return Response.ok().build();
    }
    
}
