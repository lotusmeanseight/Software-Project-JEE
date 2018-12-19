package de.ostfalia.gruppe5.business.boundary;

import de.ostfalia.gruppe5.business.controller.RestCaller;
import de.ostfalia.gruppe5.business.entity.Customer;
import de.ostfalia.gruppe5.business.entity.Employee;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;

//@RolesAllowed("EMPLOYEE")
@Stateless
public class CustomerService extends AbstractTableJPAService<Customer> {
    String targetUrl = "http://localhost:8080/sep-gruppe-5/customers";

    public String call(String id, String httpMethod, JsonObject body) {
        RestCaller rest = new RestCaller();
        return rest.callRest(this.targetUrl, id, httpMethod,body);
    }

    //@RolesAllowed("CUSTOMER")
    public Customer find(final String id) {
        String response = this.call(id, RestCaller.GET, null);
        Customer customer = new Customer();
        this.populateCustomer(response, customer);
        return customer;
    }

    //@RolesAllowed("CUSTOMER")
    public Customer find(final Integer id) {
        String response = this.call(String.valueOf(id), RestCaller.GET, null);
        Customer customer = new Customer();
        this.populateCustomer(response, customer);
        return customer;
    }

    public List<Customer> findAll() {
        String response = this.call(null, RestCaller.GET, null);
        return this.populateCustomerList(response);
    }

    //@RolesAllowed("CUSTOMER")
    public void save(final Customer entity) {
        String response = this.call(String.valueOf(entity.getCustomerNumber()), RestCaller.POST, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
    }

    public void delete(final Customer entity) {
        String response = this.call(String.valueOf(entity.getCustomerNumber()), RestCaller.DELETE, null);
    }

    public void deleteById(final String id) {
        String response = this.call(id, RestCaller.DELETE, null);
    }

    public void deleteById(final Integer id) {
        String response = this.call(String.valueOf(id), RestCaller.DELETE, null);
    }

    //@RolesAllowed("CUSTOMER")
    public Customer update(final Customer entity) {
        String response = this.call(String.valueOf(entity.getCustomerNumber()), RestCaller.UPDATE, Json.createObjectBuilder().build().getJsonObject(entity.toJson()));
        Customer customer = new Customer();
        this.populateCustomer(response, customer);
        return customer;
    }

    private List<Customer> populateCustomerList(String response) {
        JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(response);
        JsonArray array = jsonObject1.asJsonArray();
        List<Customer> list= new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            Customer customer = new Customer();
            this.populateCustomer(array.get(i).asJsonObject(),customer);
            list.add(customer);
        }
        return list;
    }

    @Inject
    EmployeeService employeeService;

    private void populateCustomer(String jsonObject, Customer customer) {
        JsonObject jsonObject1 = Json.createObjectBuilder().build().getJsonObject(jsonObject);
        this.populateCustomer(jsonObject1,customer);
    }

    private void populateCustomer(JsonObject jsonObject, Customer customer) {
        customer.setCustomerName(jsonObject.get("customerName").toString());
        customer.setState(jsonObject.getString("state"));
        customer.setCreditLimit(Double.parseDouble(jsonObject.get("creditLimit").toString()));
        customer.setPostalCode(jsonObject.getString("postalCode"));
        customer.setCountry(jsonObject.getString("country"));
        customer.setContactLastName(jsonObject.getString("contactLastName"));
        customer.setContactFirstName(jsonObject.getString("contactFirstName"));
        customer.setAddressLine1(jsonObject.getString("addressLine1"));
        customer.setAddressLine2(jsonObject.get("addressLine2").toString());
        customer.setCity(jsonObject.getString("city"));
        customer.setPhone(jsonObject.getString("phone"));

        JsonObject employee = jsonObject.getJsonObject("salesRepEmployeeNumber");
        Integer employeeID = employee.getInt("employeeNumber");
        Employee employeeObject = employeeService.find(employeeID);
        customer.setSalesRepEmployeeNumber(employeeObject);
    }

    public CustomerService() {
        setEntityClass(Customer.class);

    }

}