package de.ostfalia.gruppe5.business.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import de.ostfalia.gruppe5.business.entity.OrderDetail;
import de.ostfalia.gruppe5.business.entity.Product;
import de.ostfalia.gruppe5.business.entity.ProductLine;


@RolesAllowed("EMPLOYEE")
@Stateless
@Path("/products")
public class ProductService extends AbstractLazyJPAService<Product> {

    public ProductService() {
        settClass(Product.class);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getProducts() {
        return this.getProducts();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProduct(@PathParam("id") String id) {
        return this.find(id);
    }

    @POST
    @Path("/")
    public String postProduct(@HeaderParam("uri") String productURI) {
        Product product = new Product();
        product.setProductDescription(productURI);
        this.getEntityManager().merge(product);

        return "";
    }

    @PUT
    @Path("/{id}")
    public String putProduct(@PathParam("id") String id) {
        Product product = this.find(id);
        if (!product.getProductCode().equals(id)){
            //TODO Response 400
        }
        this.getEntityManager().merge(product);
        return "";
    }

    @DELETE
    @Path("/{id}")
    public String deleteProduct(@PathParam("id") String id) {
        Product product = this.find(id);
        if (product == null){
            //TODO Response 404
        }
        this.delete(product);
        return "";
    }

    @GET
    @Path("/{id}/assignedproductLine")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductLine getProductAssignedProductLine(@PathParam("id") String id) {
        Product product = this.find(id);
        return product.getProductLine();
    }

    @GET
    @Path("/{id}/orderDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public List<OrderDetail> getProductOrderDetails(@PathParam("id") String id) {
        Product product = this.find(id);
        //TODO Method for getting orderDetails for product
        return new ArrayList<OrderDetail>();
    }
}
