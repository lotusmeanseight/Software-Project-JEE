package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Customer;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RolesAllowed("internal-user")
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
        return em.find(Customer.class, id);
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