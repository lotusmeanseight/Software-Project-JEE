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
@Path("employees")
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
        System.out.println("############################################### POST");
        System.out.println(json);
        System.out.println("############# Employee");
        Employee employee = new Employee();
        populateEmployee(json, employee);
        System.out.println("############# Office");
        service.save(employee);
        System.out.println("############# Response");
        Employee parsed = service.find(employee.getEmployeeNumber());
        UriBuilder builder = uriinfo.getRequestUriBuilder();
        URI uri = builder.path(EmployeeRessource.class, "getEmployee").build(parsed.getEmployeeNumber());
        return Response.created(uri).build();
    }

    private void populateEmployee(JsonObject json, Employee employee) {
        employee.setLastName(json.getString("lastName"));
        employee.setFirstName(json.getString("firstName"));
        employee.setExtension(json.getString("extension"));
        employee.setEmail(json.getString("email"));
        employee.setReportsTo(json.getInt("reportsTo"));

        JsonObject officeJson = json.getJsonObject("officeCode");
        String officeId = officeJson.getString("officeCode");
        Office office = this.officeService.find(officeId);
        employee.setOfficeCode(office);
        employee.setJobTitle(json.getString("jobTitle"));

    }

    @PUT
    @Path("/{id}")
    public Response putEmployee(@PathParam("id") String id, JsonObject json) {
        System.out.println("############################################### PUT");
        Employee employee = service.find(id);
        String jsonId = json.getString("employeeNumber");
        if (!employee.getEmployeeNumber().equals(jsonId)) {
            System.out.println("################ "+id+" not same as "+jsonId);
            return Response.status(400).build();
        }
        populateEmployee(json,employee);
        System.out.println("+++++++++++++++++");
        System.out.println(employee.getEmployeeNumber());
        System.out.println(employee.getFirstName());
        System.out.println(employee.getLastName());
        System.out.println(employee.getEmail());
        System.out.println(employee.getExtension());
        System.out.println(employee.getJobTitle());
        System.out.println(employee.getReportsTo());
        System.out.println(employee.getOfficeCode());
        System.out.println("+++++++++++++++++");
        service.update(employee);

        GenericEntity<Employee> entity = new GenericEntity<>(service.find(id), Employee.class);
        return Response.ok().entity(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") String id) {
        System.out.println("############################################### DELETE");
        Employee employee = service.find(id);
        if (employee == null) {
            return Response.status(404).build();
        }
        GenericEntity<Employee> entity = new GenericEntity<>(employee, Employee.class);
        //TODO detached entity
        service.deleteById(id);
        return Response.ok().entity(entity).build();
    }


}
