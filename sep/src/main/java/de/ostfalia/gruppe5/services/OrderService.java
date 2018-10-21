package de.ostfalia.gruppe5.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.ostfalia.gruppe5.models.interfaces.Order;
import de.ostfalia.gruppe5.models.implementations.OrderImpl;

@Stateless
public class OrderService {

	@PersistenceContext(name = "simple")
	EntityManager entityManager;

	public OrderService() {

	}

	public void save(Order order) {
		entityManager.persist(order);
	}

	private Order findById(Integer id) {
		return entityManager.find(OrderImpl.class, id);
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
