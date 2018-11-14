package de.ostfalia.gruppe5.views;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.EmployeeService;
import de.ostfalia.gruppe5.business.entity.Employee;

@ViewScoped
@Named
public class EmployeeDataTable extends Datatable<EmployeeService, Employee> {

	@Inject
	private EmployeeService service;

	public EmployeeDataTable() {

	}

}
