package de.ostfalia.gruppe5.views;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.business.boundary.CustomerService;
import de.ostfalia.gruppe5.business.boundary.ProductBasket;
import de.ostfalia.gruppe5.business.entity.Customer;

@RequestScoped
@Named
public class CustomerView {

	@Inject
	private ProductBasket productBasket;

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

	public void addCustomerToBasket(Customer customer) {
		productBasket.setCustomer(customer);
	}

	public CustomerDataTable getDatatable() {
		return datatable;
	}
}
