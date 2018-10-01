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

    public void mergeById(Long id) {
        em.merge(findById(id));
    }

    private void merge(Customer customer) {
        em.merge(customer);
    }

    private void detachCustomer(Customer customer) {
        em.detach(customer);
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

    public Customer update(String id) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        Customer detachedCustomer = this.findById(Long.valueOf(id));
        this.detachCustomer(detachedCustomer);
        Date birthdayDate= null;
        try {
            birthdayDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(request.getParameter("customer_birthdate_input_"+id));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        detachedCustomer.setBirthdate(birthdayDate);
        detachedCustomer.setFirstname(request.getParameter("customer_firstname_input_"+id));
        detachedCustomer.setLastname(request.getParameter("customer_lastname_input_"+id));
        this.merge(detachedCustomer);
        return detachedCustomer;
    }
}