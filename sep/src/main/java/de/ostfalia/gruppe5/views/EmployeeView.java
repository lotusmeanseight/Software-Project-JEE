package de.ostfalia.gruppe5.views;

import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.entity.DataModel;
import de.ostfalia.gruppe5.business.entity.Employee;
import de.ostfalia.gruppe5.business.boundary.EmployeeService;

@RequestScoped
@Named
public class EmployeeView {

	private Employee employee;
	@Inject
	private EmployeeService service;

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

}
