package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.ProductService;
import de.ostfalia.gruppe5.business.entity.OrderDetail;
import de.ostfalia.gruppe5.business.entity.Product;
import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Path("/products")
public class ProductRessource {

    @Inject
    private ProductService productService;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts() {
        return productService.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProduct(@PathParam("id") String id) {
        return productService.find(id);
    }

    @POST
    @Path("/")
    public String postProduct(@HeaderParam("uri") String productURI) {
        Product product = new Product();
        product.setProductDescription(productURI);
        productService.getEntityManager().merge(product);
        return "";
    }

    @PUT
    @Path("/{id}")
    public Response putProduct(@PathParam("id") String id) {
        Product product = productService.find(id);
        if (!product.getProductCode().equals(id)){
            //TODO Response 400
        }
        productService.getEntityManager().merge(product);

        GenericEntity<Product> entity = new GenericEntity<>(productService.find(id),Product.class);
        return Response.ok().entity(entity).build();
    }

    /*
     * Delete nur für Produkte die noch nie bestellt wurden!.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") String id) {
        Product product = productService.find(id);
        GenericEntity<Product> entity = new GenericEntity<>(productService.find(id),Product.class);
        if (product == null){
            //TODO Response 404
        }
        productService.delete(product);
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("/{id}/assignedproductLine")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductLine getProductAssignedProductLine(@PathParam("id") String id) {
        Product product = productService.find(id);
        return product.getProductLine();
    }
}
