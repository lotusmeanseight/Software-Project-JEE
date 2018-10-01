package de.ostfalia.gruppe5.views;

import de.ostfalia.gruppe5.models.Customer;
import de.ostfalia.gruppe5.services.CustomerService;

import javax.annotation.ManagedBean;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public void save() {
        service.save(customer);
    }

    public void update() {
        Customer detachedCustomer = service.findById(customer.getId());
        service.detachCustomer(detachedCustomer);
        detachedCustomer.setBirthdate(this.customer.getBirthdate());
        detachedCustomer.setFirstname(this.customer.getFirstname());
        detachedCustomer.setLastname(this.customer.getLastname());
        service.merge(detachedCustomer);
    }
    public void update(String id) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        Customer detachedCustomer = service.findById(Long.valueOf(id));
        service.detachCustomer(detachedCustomer);
        Date birthdayDate= null;
        try {
            birthdayDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(request.getParameter("customer_birthdate_input_"+id));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detachedCustomer.setBirthdate(birthdayDate);
        detachedCustomer.setFirstname(request.getParameter("customer_firstname_input_"+id));
        detachedCustomer.setLastname(request.getParameter("customer_lastname_input_"+id));
        service.merge(detachedCustomer);
    }

    public void delete(String id) {
        service.deleteById(Long.valueOf(id));
	}

	public void delete() {
        service.deleteById(customer.getId());
	}
}
