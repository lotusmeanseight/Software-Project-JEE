package de.ostfalia.gruppe5.views;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.Customer;
import de.ostfalia.gruppe5.services.CustomerService;

import java.util.List;

@RequestScoped
@Named
public class CustomerView {

    private Customer customer;

    @Inject
    private CustomerService service;

    public CustomerView() {
    	customer = new Customer();
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public List<Customer> getCustomers() {
		return service.getAllCustomers();
	}

	public String save() {
        service.save(customer);
        return null;
    }
	
	public String merge() {
		service.merge(customer.getId());
		return null;
	}
	
	public String delete() {
		service.delete(customer.getId());
		return null;
	}
}
