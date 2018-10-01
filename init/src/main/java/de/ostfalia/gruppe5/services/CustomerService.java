package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Customer;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Stateless
public class CustomerService {

    @PersistenceContext
    EntityManager em;
    
    public CustomerService() {
    	
    }

    public void save(Customer customer) {
        em.persist(customer);
    }

    private Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public void deleteById(Long id) {
        em.remove(findById(id));
    }

    public List<Customer> getAllCustomers() {
        return em.createQuery("SELECT c from Customer c",Customer.class).getResultList();
    }

    public Customer update(Customer customer) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        Customer detachedCustomer = this.findById(customer.getId());

        Date birthdayDate= null;
        try {
            birthdayDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(request.getParameter("customer_birthdate_input_"+customer.getId()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detachedCustomer.setBirthdate(birthdayDate);
        detachedCustomer.setFirstname(request.getParameter("customer_firstname_input_"+customer.getId()));
        detachedCustomer.setLastname(request.getParameter("customer_lastname_input_"+customer.getId()));
        return detachedCustomer;
    }
}