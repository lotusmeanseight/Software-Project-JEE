package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.controller.RestCaller;
import de.ostfalia.gruppe5.business.entity.Employee;
import de.ostfalia.gruppe5.business.entity.Office;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

//@RolesAllowed("EMPLOYEE")
@Stateless
public class EmployeeService extends AbstractTableJPAService<Employee> {
    String targetUrl = null;

    public String call(String id, String httpMethod, JsonObject body) {
        RestCaller rest = new RestCaller();
        return rest.callRest(this.targetUrl, id, httpMethod, body);
    }

    //@RolesAllowed("CUSTOMER")
    public Employee find(final String id) {
        String response = this.call(id, RestCaller.GET, null);
        Employee employee = new Employee();
        this.populateEmployee(response, employee);
        return employee;
    }

    //@RolesAllowed("CUSTOMER")
    public Employee find(final Integer id) {
        String response = this.call(String.valueOf(id), RestCaller.GET, null);
        Employee employee = new Employee();
        this.populateEmployee(response, employee);
        return employee;
    }

    public List<Employee> findAll() {
        String response = this.call(null, RestCaller.GET, null);
        Employee employee = new Employee();
        return this.populateEmployeeList(response);
    }

    //@RolesAllowed("CUSTOMER")
    public void save(final Employee entity) {
        String response = this.call(String.valueOf(entity.getEmployeeNumber()), RestCaller.POST, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
    }

    public void delete(final Employee entity) {
        String response = this.call(String.valueOf(entity.getEmployeeNumber()), RestCaller.DELETE, null);
    }

    public void deleteById(final String id) {
        String response = this.call(id, RestCaller.DELETE, null);
    }

    public void deleteById(final Integer id) {
        String response = this.call(String.valueOf(id), RestCaller.DELETE, null);
    }

    //@RolesAllowed("CUSTOMER")
    public Employee update(final Employee entity) {
        String response = this.call(String.valueOf(entity.getEmployeeNumber()), RestCaller.UPDATE, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
        Employee employee = new Employee();
        this.populateEmployee(response, employee);
        return employee;
    }

    @Inject
    private OfficeService officeService;

    private List<Employee> populateEmployeeList(String response) {
        JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(response);
        JsonArray array = jsonObject1.asJsonArray();
        List<Employee> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            Employee employee = new Employee();
            this.populateEmployee(array.get(i).asJsonObject(), employee);
            list.add(employee);
        }
        return list;
    }

    private void populateEmployee(String json, Employee employee) {
        JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(json);
        this.populateEmployee(jsonObject1, employee);
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


    public EmployeeService() {
        setEntityClass(Employee.class);
    }

}
