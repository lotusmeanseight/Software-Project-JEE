package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.EmployeeService;
import de.ostfalia.gruppe5.business.entity.Employee;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class EmployeeView {

	private Employee employee;
	@Inject
	private EmployeeService service;
	@Inject
	private EmployeeDataTable datatable;

	public EmployeeView() {
		employee = new Employee();
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

	public EmployeeDataTable getDatatable() {
		return datatable;
	}

}
