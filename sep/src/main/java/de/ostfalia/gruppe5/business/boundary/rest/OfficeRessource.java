package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.OfficeService;
import de.ostfalia.gruppe5.business.entity.Office;

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
@Path("offices")
public class OfficeRessource {

    @Inject
    private OfficeService service;

    public OfficeRessource(){
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Office> getOffices(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Office getOffice(@PathParam("id") Integer id){
        return service.find(id);
    }

}
