package de.ostfalia.gruppe5.views;

import java.util.TreeSet;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.Customer;
import de.ostfalia.gruppe5.models.DataModel;
import de.ostfalia.gruppe5.services.CustomerService;

@RequestScoped
@Named
public class CustomerView {

	private Customer customer;
	@Inject
	private CustomerService service;
	private DataModel customerDataModel;
	private HtmlDataTable table;
	private int rowsOnPage;
	private int allRowsCount = 0;

	@PostConstruct
	public void initHashSet() {
		rowsOnPage = 10; // Gibt die Anzahl an Einträgen an, die Pro Seite abgebildet werden
		allRowsCount = service.countCustomers(); // Zählt die Einträge in der Datenbank
		lazyDataLoading(0);
	}

	private void lazyDataLoading(int first) {
		TreeSet<Customer> dataTreeSet = service.getAllCustomersLazy(first, rowsOnPage);
		customerDataModel = new DataModel(dataTreeSet, allRowsCount, rowsOnPage);
	}

	public CustomerView() {
		customer = new Customer();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public DataModel getCustomerDataModel() {
		return customerDataModel;
	}

	public void setCustomerDataModel(DataModel customerDataModel) {
		this.customerDataModel = customerDataModel;
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

	public String save() {
		service.save(customer);
		return null;
	}

	public String update(Customer customer) {
		service.update(customer);
		return null;
	}

	public String delete(Customer customer) {
		service.deleteById(customer.getCustomerNumber());
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
