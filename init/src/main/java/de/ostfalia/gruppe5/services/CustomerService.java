package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Customer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    public void merge(Customer customer) {
        em.merge(customer);
    }

    public void detachCustomer(Customer customer) {
        em.detach(customer);
    }

    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    public void deleteById(Long id) {
        em.remove(findById(id));
    }

    public List<Customer> getAllCustomers() {
        return em.createQuery("SELECT c from Customer c").getResultList();
    }
}