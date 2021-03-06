package de.ostfalia.gruppe5.business.boundary.rest;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
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

import de.ostfalia.gruppe5.business.boundary.ProductLineService;
import de.ostfalia.gruppe5.business.entity.ProductLine;

@RolesAllowed("EMPLOYEE")
@Stateless
@Path("/productlines")
public class ProductLineRessource {

	@Inject
	private ProductLineService productLineService;
	@Context
	private UriInfo uriinfo;

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
		ProductLine productLine = new ProductLine();
		productLine.setProductLine(productLineService.nextID());
		populateProductLine(json, productLine);
		productLineService.save(productLine);
		ProductLine parsed = productLineService.find(productLine.getProductLine());
		UriBuilder builder = uriinfo.getRequestUriBuilder();
		URI uri = builder.path(ProductLineRessource.class, "getProductLine").build(parsed.getProductLine());
		return Response.created(uri).build();
	}

	private void populateProductLine(JsonObject json, ProductLine productLine) {
		productLine.setHtmlDescription(json.get("htmlDescription").toString());
		productLine.setTextDescription(json.get("textDescription").toString());

		productLine.setImage(json.get("image").toString().getBytes());
	}

	@PUT
	@Path("/{id}")
	public Response putProductLine(@PathParam("id") String id, JsonObject json) {
		ProductLine productLine = productLineService.find(id);
		String jsonId = json.getString("productLine");
		if (!productLine.getProductLine().equals(jsonId)) {
			return Response.status(400).build();
		}
		populateProductLine(json, productLine);
		productLineService.update(productLine);

		GenericEntity<ProductLine> entity = new GenericEntity<>(productLineService.find(id), ProductLine.class);
		return Response.ok().entity(entity).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteProductLine(@PathParam("id") String id) {
		ProductLine productLine = productLineService.find(id);
		if (productLine == null) {
			return Response.status(404).build();
		}
		productLineService.deleteById(id);
		return Response.ok().build();
	}

}
