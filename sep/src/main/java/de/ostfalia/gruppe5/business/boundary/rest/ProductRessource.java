package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.ProductLineService;
import de.ostfalia.gruppe5.business.boundary.ProductService;
import de.ostfalia.gruppe5.business.entity.Product;
import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Path("/products")
public class ProductRessource {

    @Inject
    private ProductService productService;
    @Inject
    private ProductLineService productLineService;
    @Context
    private UriInfo uriinfo;

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
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProduct(JsonObject json) {
        Product product = new Product();
        product.setProductCode(productService.nextID());
        populateProduct(json, product);
        productService.save(product);
        Product parsed = productService.find(product.getProductCode());
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(ProductRessource.class, "getProduct").build(parsed.getProductCode());
        return Response.created(uri).build();
    }

    private void populateProduct(JsonObject json, Product product) {
        product.setProductDescription(json.getString("productDescription"));
        product.setProductVendor(json.getString("productVendor"));
        product.setProductName(json.getString("productName"));
        product.setQuantityInStock(json.getInt("quantityInStock"));
        product.setBuyPrice(json.getJsonNumber("buyPrice").doubleValue());
        product.setMSRP(json.getJsonNumber("msrp").doubleValue());
        product.setProductScale(json.getString("productScale"));

        JsonObject productLineJson = json.getJsonObject("productLine");
        String productLineId = productLineJson.getString("productLine");
        ProductLine productLine = this.productLineService.find(productLineId);
        product.setProductLine(productLine);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putProduct(@PathParam("id") String id, JsonObject json) {
        Product product = productService.find(id);
        String jsonId = json.getString("productCode");
        if (!product.getProductCode().equals(jsonId)) {
            return Response.status(400).build();
        }
        populateProduct(json,product);
        productService.update(product);

        GenericEntity<Product> entity = new GenericEntity<>(productService.find(id), Product.class);
        return Response.ok().entity(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteProduct(@PathParam("id") String id) {
        Product product = productService.find(id);
        if (product == null) {
            return Response.status(404).build();
        }
        GenericEntity<Product> entity = new GenericEntity<>(product, Product.class);
        productService.deleteById(id);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/assignedproductLine")
    @Produces(MediaType.APPLICATION_JSON)
    public ProductLine getProductAssignedProductLine(@PathParam("id") String id) {
        Product product = productService.find(id);
        return product.getProductLine();
    }
}
