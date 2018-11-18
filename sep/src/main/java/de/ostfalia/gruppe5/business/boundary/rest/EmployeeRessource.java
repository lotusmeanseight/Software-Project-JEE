package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.EmployeeService;
import de.ostfalia.gruppe5.business.entity.Employee;

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
@Path("employees")
public class EmployeeRessource {

    @Inject
    private EmployeeService service;

    public EmployeeRessource(){
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getEmployees(){
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Employee getEmployee(@PathParam("id") Integer id){
        return service.find(id);
    }

}
