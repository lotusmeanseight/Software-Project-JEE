package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.models.Customer;
import de.ostfalia.gruppe5.services.CustomerService;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@ManagedBean
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

    public String update(String id) {
        service.update(id);
        return null;
    }

    public String delete(String id) {
        service.deleteById(Long.valueOf(id));
        return null;
	}
}
