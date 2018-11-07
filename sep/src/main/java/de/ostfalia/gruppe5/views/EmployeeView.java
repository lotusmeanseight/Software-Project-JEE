package de.ostfalia.gruppe5.views;

import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.DataModel;
import de.ostfalia.gruppe5.models.Employee;
import de.ostfalia.gruppe5.services.EmployeeService;

@RequestScoped
@Named
public class EmployeeView {

	private Employee employee;
	@Inject
	private EmployeeService service;

	public EmployeeView() {
		employee = new Employee();
	}

	private DataModel employeeDataModel;
	private HtmlDataTable table;
	private int rowsOnPage;
	private int allRowsCount = 0;

	@PostConstruct
	public void initSet() {
		rowsOnPage = 10; // Gibt die Anzahl an Einträgen an, die Pro Seite abgebildet werden
		allRowsCount = service.countEmployees(); // Zählt die Einträge in der Datenbank
		lazyDataLoading(0);
	}

	private void lazyDataLoading(int first) {
		TreeSet<Employee> dataTreeSet = service.getAllEmployeesLazy(first, rowsOnPage);
		employeeDataModel = new DataModel(dataTreeSet, allRowsCount, rowsOnPage);
	}

	public int getRowsOnPage() {
		return rowsOnPage;
	}

	public void setRowsOnPage(int rowsOnPage) {
		this.rowsOnPage = rowsOnPage;
	}

	public HtmlDataTable getTable() {
		return table;
	}

	public void setTable(HtmlDataTable table) {
		this.table = table;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public DataModel getEmployeeDataModel() {
		return employeeDataModel;
	}

	public void setEmployeeDataModel(DataModel employeeDataModel) {
		this.employeeDataModel = employeeDataModel;
	}

	public int getAllRowsCount() {
		return allRowsCount;
	}

	public void setAllRowsCount(int allRowsCount) {
		this.allRowsCount = allRowsCount;
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

	public void goToFirstPage() {
		table.setFirst(0);
		lazyDataLoading(0);
	}

	public void goToPreviousPage() {
		table.setFirst(table.getFirst() - table.getRows());
		lazyDataLoading(table.getFirst());
	}

	public void goToNextPage() {
		table.setFirst(table.getFirst() + table.getRows());
		lazyDataLoading(table.getFirst());
	}

	public void goToLastPage() {
		int totalRows = table.getRowCount();
		int displayRows = table.getRows();
		int full = totalRows / displayRows;
		int modulo = totalRows % displayRows;

		if (modulo > 0) {
			table.setFirst(full * displayRows);
		} else {
			table.setFirst((full - 1) * displayRows);
		}

		lazyDataLoading(table.getFirst());
	}

}
