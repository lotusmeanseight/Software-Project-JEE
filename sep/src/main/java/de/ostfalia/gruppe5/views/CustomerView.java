package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.models.Customer;
import de.ostfalia.gruppe5.services.CustomerService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
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

    public String update(Customer customer) {
        service.update(customer);
        return null;
    }

    public String delete(Customer customer) {
        service.deleteById(customer.getCustomerNumber());
        return null;
	}
}
