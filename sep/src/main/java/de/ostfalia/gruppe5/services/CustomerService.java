package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.interfaces.Customer;
import de.ostfalia.gruppe5.models.implementations.CustomerImpl;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Stateless
public class CustomerService {

    @PersistenceContext(unitName = "simple")
    EntityManager em;


    public CustomerService() {
    }

    public void save(Customer customer) {
        em.persist(customer);
    }

    private Customer findById(Integer id) {
        return em.find(CustomerImpl.class, id);
    }

    public void deleteById(Integer id) {
        em.remove(findById(id));
    }

    public List<Customer> getAllCustomers() {
        return em.createQuery("SELECT c from Customer c", Customer.class).getResultList();
    }


    public Customer update(Customer customer) {
        return em.merge(customer);
    }
}