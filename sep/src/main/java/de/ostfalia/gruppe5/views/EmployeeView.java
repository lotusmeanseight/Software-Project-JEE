package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.models.Employee;
import de.ostfalia.gruppe5.services.EmployeeService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class EmployeeView {

    private Employee employee;
    @Inject
    private EmployeeService service;

    public EmployeeView() {
        employee = new Employee();
    }

    public List<Employee> getEmployees() {
        return service.getAllEmployees();
    }

    public String save() {
        service.save(employee);
        return null;
    }

    public String delete(Employee employee) {
        service.deleteById(employee.getEmployeeNumber());
        return null;
    }

    public String update(Employee employee) {
        service.update(employee);
        return null;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
