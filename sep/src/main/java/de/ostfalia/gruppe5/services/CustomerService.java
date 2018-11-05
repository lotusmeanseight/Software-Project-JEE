package de.ostfalia.gruppe5.services;

import de.ostfalia.gruppe5.models.Customer;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RolesAllowed("EMPLOYEE")
@Stateless
public class CustomerService {

    @PersistenceContext(unitName = "simple")
    EntityManager entityManager;


    public CustomerService() {
    }

    public void save(Customer customer) {
        entityManager.persist(customer);
    }

    public Customer findById(Integer id) {
        return entityManager.find(Customer.class, id);
    }

    public void deleteById(Integer id) {
        entityManager.remove(findById(id));
    }

    public List<Customer> getAllCustomers() {
        return entityManager.createQuery("SELECT c from Customer c", Customer.class).getResultList();
    }


    public Customer update(Customer customer) {
        return entityManager.merge(customer);
    }
}