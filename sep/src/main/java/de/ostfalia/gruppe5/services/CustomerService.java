package de.ostfalia.gruppe5.services;

import java.util.HashSet;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.ostfalia.gruppe5.models.Customer;

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

	public HashSet<Customer> getAllCustomersLazy(int first, int max) {
		Query query = entityManager.createNamedQuery("Customer.findAll");
		query.setFirstResult(first);
		query.setMaxResults(max);
		return new HashSet(query.getResultList());
	}

	public int countCustomers() {
		Query query = entityManager.createNamedQuery("Customer.countAll");
		return ((Long) query.getSingleResult()).intValue();
	}
}