package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Customer;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
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
        Customer detachedCustomer = this.findById(customer.getId());
        detachedCustomer.setBirthdate(customer.getBirthdate());
        detachedCustomer.setFirstname(customer.getFirstname());
        detachedCustomer.setLastname(customer.getLastname());
        detachedCustomer.setId(customer.getId());

        return detachedCustomer;
    }
}