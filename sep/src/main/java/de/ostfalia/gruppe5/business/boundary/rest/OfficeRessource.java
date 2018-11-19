package de.ostfalia.gruppe5.business.boundary.rest;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import de.ostfalia.gruppe5.business.boundary.OfficeService;
import de.ostfalia.gruppe5.business.entity.Office;

@RolesAllowed("EMPLOYEE")
@Stateless
@Path("offices")
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
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createOffice(Office office) {

		service.save(office);
		Office o = service.find(office.getOfficeCode());
		UriBuilder builder = uriinfo.getRequestUriBuilder();
		URI uri = builder.path(OfficeRessource.class, "getOffice").build(o.getOfficeCode());
		return Response.created(uri).build();
	}

	@DELETE
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteOffice(@PathParam("id") Integer id) {
		Office office = service.find(id);
		if (office == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			service.delete(office);
			return Response.ok().build();
		}
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateOffice(@PathParam("id") Integer id, Office office) {
		if (id != office.getOfficeCode()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Office o = service.update(office);
		if (o == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok().build();
		}

	}
}
