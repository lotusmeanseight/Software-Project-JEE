package de.ostfalia.gruppe5.services;

import java.util.List;
import java.util.TreeSet;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import de.ostfalia.gruppe5.models.Customer;
import de.ostfalia.gruppe5.views.comparators.CustomerComparator;

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

	public TreeSet<Customer> getAllCustomersLazy(int first, int max) {
		TypedQuery<Customer> query = entityManager.createNamedQuery("Customer.findAll", Customer.class);
		query.setFirstResult(first);
		query.setMaxResults(max);
		TreeSet<Customer> treeSet = new TreeSet<Customer>(new CustomerComparator());
		for (Customer c : query.getResultList()) {
			treeSet.add(c);
		}
		return treeSet;
	}

	public int countCustomers() {
		Query query = entityManager.createNamedQuery("Customer.countAll");
		return ((Long) query.getSingleResult()).intValue();
	}
}