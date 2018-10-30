package de.ostfalia.gruppe5.services;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.models.Order;

@DeclareRoles({"internal-user", "customer"})
@RolesAllowed("internal-user")
@Stateless
public class OrderService {

	@PersistenceContext(name = "simple")
	EntityManager entityManager;

	public OrderService() {

	}

	public void save(Order order) {
		entityManager.persist(order);
	}

	@RolesAllowed("customer")
	private Order findById(Integer id) {
		return entityManager.find(Order.class, id);
	}

	public void deleteById(Integer id) {
		entityManager.remove(findById(id));
	}

	public List<Order> getAllOrders() {
		return entityManager.createQuery("select o from Order o", Order.class).getResultList();
	}

	public Order update(Order order) {
		return entityManager.merge(order);
	}

}
