package de.ostfalia.gruppe5.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.models.Customer;

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
    
    public void merge(Customer customer) {
    	em.merge(customer);
    }
    
    public void merge(Long id) {
    	em.merge(find(id));
    }

    public Customer find(Long id) {
    	return em.find(Customer.class, id);
    }
    
    public void delete(Customer customer) {
    	em.remove(customer);
    }
    
    public void delete(Long id) {
    	em.remove(find(id));
    }
    
    @SuppressWarnings("unchecked")
	public List<Customer> getAllCustomers(){
       return (List<Customer>) em.createQuery("SELECT c from Customer c").getResultList();
    }
}