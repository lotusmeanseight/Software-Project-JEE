package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.OfficeService;
import de.ostfalia.gruppe5.business.entity.Office;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.net.URI;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
@Path("/offices")
public class OfficeRessource {

	@Inject
	private OfficeService service;

	@Context
	private UriInfo uriinfo;

	public OfficeRessource() {
	}

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOffices() {
		List<Office> offices = service.findAll();
		if (offices.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		} else {

			return Response.ok(offices).build();
		}
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Office getOffice(@PathParam("id") Integer id) {
		return service.find(id);
	}

	@POST
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postOffice(JsonObject json) {
		Office office = new Office();
		populateOffice(json,office);
		service.save(office);
		Office parsed = service.find(office.getOfficeCode());
		UriBuilder builder = uriinfo.getRequestUriBuilder();
		URI uri = builder.path(OfficeRessource.class,"getOffice").build(parsed.getOfficeCode());
		return Response.created(uri).build();
	}

	private void populateOffice(JsonObject json, Office office) {
		office.setCity(json.getString("city"));
		office.setPhone(json.getString("phone"));
		office.setAddressLine1(json.getString("addressLine1"));
		office.setAddressLine2(json.getString("addressLine2"));
		office.setState(json.getString("state"));
		office.setCountry(json.getString("country"));
		office.setPostalCode(json.get("postalCode").toString());
		office.setTerritory(json.getString("territory"));
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteOffice(@PathParam("id") Integer id) {
		Office office = service.find(id);
		if (office == null) {
			return Response.status(404).build();
		}
		GenericEntity<Office> entity = new GenericEntity<>(office, Office.class);
		service.deleteById(id);
		return Response.ok().entity(entity).build();
	}

	@PUT
	@Path("/{id}")
	public Response putOffice(@PathParam("id") Integer id, JsonObject json) {
		Office office = service.find(id);
		int jsonId = json.getInt("officeCode");
		if (!office.getOfficeCode().equals(jsonId)) {
			return Response.status(400).build();
		}
		populateOffice(json,office);
		service.update(office);

		GenericEntity<Office> entity = new GenericEntity<>(service.find(id), Office.class);
		return Response.ok().entity(entity).build();
	}
}
