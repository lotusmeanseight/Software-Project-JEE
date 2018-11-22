package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.EmployeeService;
import de.ostfalia.gruppe5.business.entity.Employee;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named
public class EmployeeDataTable extends Datatable<EmployeeService, Employee> {

	@Inject
	private EmployeeService service;

	public EmployeeDataTable() {

	}

}
