package de.ostfalia.gruppe5.business.boundary.rest;

import de.ostfalia.gruppe5.business.boundary.EmployeeService;
import de.ostfalia.gruppe5.business.boundary.OfficeService;
import de.ostfalia.gruppe5.business.entity.Employee;
import de.ostfalia.gruppe5.business.entity.Office;
import de.ostfalia.gruppe5.business.entity.ProductLine;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
@Path("/employees")
public class EmployeeRessource {

    @Inject
    private EmployeeService service;

    @Inject
    private OfficeService officeService;

    @Context
    private UriInfo uriinfo;

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

    @POST
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postEmployee(JsonObject json) {
        Employee employee = new Employee();
        populateEmployee(json, employee);
        service.save(employee);
        Employee parsed = service.find(employee.getEmployeeNumber());
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(EmployeeRessource.class, "getEmployee").build(parsed.getEmployeeNumber());
        return Response.created(uri).build();
    }

    private void populateEmployee(JsonObject json, Employee employee) {
        employee.setLastName(json.getString("lastName"));
        employee.setFirstName(json.getString("firstName"));
        employee.setExtension(json.getString("extension"));
        employee.setEmail(json.get("email").toString());
        employee.setReportsTo(json.getInt("reportsTo"));

        JsonObject officeJson = json.getJsonObject("officeCode");
        Integer officeId = Integer.parseInt(officeJson.get("officeCode").toString());
        Office office = this.officeService.find(officeId);
        employee.setOfficeCode(office);
        employee.setJobTitle(json.getString("jobTitle"));

    }

    @PUT
    @Path("/{id}")
    public Response putEmployee(@PathParam("id") Integer id, JsonObject json) {
        Employee employee = service.find(id);
        int jsonId = json.getInt("employeeNumber");
        if (!employee.getEmployeeNumber().equals(jsonId)) {
            return Response.status(400).build();
        }
        populateEmployee(json,employee);
        service.update(employee);

        GenericEntity<Employee> entity = new GenericEntity<>(service.find(id), Employee.class);
        return Response.ok().entity(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEmployee(@PathParam("id") Integer id) {
        Employee employee = service.find(id);
        if (employee == null) {
            return Response.status(404).build();
        }
        GenericEntity<Employee> entity = new GenericEntity<>(employee, Employee.class);
        service.deleteById(id);
        return Response.ok().entity(entity).build();
    }

    @GET
    @Path("/{id}/assignedOffice")
    @Produces(MediaType.APPLICATION_JSON)
    public Office getAssignedOffice(@PathParam("id") Integer id){
        Employee employee = service.find(id);
        return employee.getOfficeCode();
    }
}
