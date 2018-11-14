package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.business.boundary.CustomerService;
import de.ostfalia.gruppe5.business.entity.Customer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped
@Named
public class CustomerView {

	private Customer customer;
	@Inject
	private CustomerService service;
	@Inject
	private CustomerDataTable datatable;

	public CustomerView() {
		customer = new Customer();
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public CustomerDataTable getDatatable() {
		return datatable;
	}
}
