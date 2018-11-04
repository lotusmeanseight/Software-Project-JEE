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
	EntityManager em;

	public CustomerService() {
	}

	public void save(Customer customer) {
		em.persist(customer);
	}

	public Customer findById(Integer id) {
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

	public HashSet<Customer> getAllCustomersLazy(int first, int max) {
		Query query = em.createNamedQuery("Customer.findAll");
		query.setFirstResult(first);
		query.setMaxResults(max);
		return new HashSet(query.getResultList());
	}

	public int countCustomers() {
		Query query = em.createNamedQuery("Customer.countAll");
		return ((Long) query.getSingleResult()).intValue();
	}
}