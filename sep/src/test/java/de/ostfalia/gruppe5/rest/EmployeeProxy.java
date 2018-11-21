package de.ostfalia.gruppe5.rest;

import de.ostfalia.gruppe5.business.entity.Office;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/employees")
public interface EmployeeProxy extends BasicProxy {
    @GET
    @Path("/{id}/assignedOffice")
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getAssignedOffice(@PathParam("id") String id);
}