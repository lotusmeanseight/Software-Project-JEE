package de.ostfalia.gruppe5.services;

import java.util.HashSet;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.ostfalia.gruppe5.models.Order;

@DeclareRoles({ "EMPLOYEE", "CUSTOMER" })
@RolesAllowed("EMPLOYEE")
@Stateless
public class OrderService {

	@PersistenceContext(name = "simple")
	EntityManager entityManager;

	public OrderService() {

	}

	public void save(Order order) {
		entityManager.persist(order);
	}

	public Order findById(Integer id) {
		return entityManager.find(Order.class, id);
	}

	public void deleteById(Integer id) {
		entityManager.remove(findById(id));
	}

	public List<Order> getAllOrders() {
		return entityManager.createQuery("select o from Order o", Order.class).getResultList();
	}

	@RolesAllowed("CUSTOMER")
	public List<Order> getOrdersByCustomerId(Integer id) {
		return entityManager.createQuery("select o from Order o where o.customerNumber = " + id, Order.class)
				.getResultList();
	}

	public Order update(Order order) {
		return entityManager.merge(order);
	}

	public HashSet<Order> getAllOrdersLazy(int first, int max) {
		Query query = entityManager.createNamedQuery("Order.findAll");
		query.setFirstResult(first);
		query.setMaxResults(max);
		return new HashSet(query.getResultList());
	}

	public int countOrders() {
		Query query = entityManager.createNamedQuery("Order.countAll");
		return ((Long) query.getSingleResult()).intValue();
	}

}
