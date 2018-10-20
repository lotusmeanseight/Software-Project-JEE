package de.ostfalia.gruppe5.views;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import de.ostfalia.gruppe5.models.Order;
import de.ostfalia.gruppe5.services.OrderService;

@Named
@RequestScoped
public class OrderView {

	private Order order;

	@Inject
	private OrderService service;

	public OrderView() {
		order = new Order();
	}

	public List<Order> getOrders() {
		return service.getAllOrders();
	}

	public String save() {
		service.save(order);
		return null;
	}

	public String delete(Order order) {
		service.deleteById(order.getOrderNumber());
		return null;
	}

	public String update(Order order) {
		service.update(order);
		return null;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
